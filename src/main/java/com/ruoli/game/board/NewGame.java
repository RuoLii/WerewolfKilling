package com.ruoli.game.board;

import com.ruoli.game.Utils.RandomUtils;
import com.ruoli.game.Utils.TimerUtils;
import com.ruoli.game.roles.*;

import java.util.*;
import java.util.stream.Collectors;


public abstract class NewGame {
    //  角色列表
    protected final List<Role> roleList = new ArrayList<>();
    //  所有游戏过程中产生的信息存放在这里，游戏后可查看
    protected final List<String> operationList = new ArrayList<>();

    public NewGame() throws InterruptedException {
        //  ====  分配身份  ====
        roleReady();
        //  ====  开始游戏  ====
        gameStart();
        //  ====  游戏结算  ====
        gameSettlement();
    }

    /**
     * 分配角色及号码牌
     */
    protected abstract void roleReady();

    /**
     * 开始游戏
     * (夜晚 ——> 白天 为一次循环)
     */
    private void gameStart() throws InterruptedException {
        System.out.println("**********************************\niwei：“游戏即将开始，请各位玩家做好准备！！”\n**********************************");
        int day = 0; //  进行了几晚
        List<Role> deathList = new ArrayList<>();
        Role protectedRole = null;
        Boolean isWitchSave = false;
        Role poisonedRole = null;
        while (true) {
            TimerUtils.timer();
            day++;
            deathList.clear();
            protectedRole = null;
            isWitchSave = false;
            System.out.println("\n------------（第 " + day + " 晚）------------");
            System.out.println("天黑请闭眼......\n");
            TimerUtils.timer();

            //  狼人/狼王开始行动
            Werewolf.start(day);
            //  狼人投票、杀人事件
            Role killedRole = Werewolf.werewolfKill(roleList);
            operationList.add("第" + day + "天夜晚，狼人阵营向 " + killedRole.id + " 号玩家（" + killedRole.name + "）出刀。");
            deathList.add(killedRole);
            //  狼人/狼王结束行动
            Werewolf.end();

            if (!roleList.stream().filter(item -> item.name == "预言家").collect(Collectors.toList()).isEmpty()) {
                //  预言家开始行动
                Prophet.start();
                //  预言家验人
                Role checkRole = Prophet.check(roleList);
                operationList.add("第" + day + "天夜晚，预言家查验了 " + checkRole.id + " 号玩家（" + checkRole.name + "）。");
                //  预言家结束行动
                Prophet.end();
            }


            if (!roleList.stream().filter(item -> item.name == "女巫").collect(Collectors.toList()).isEmpty()) {
                //  女巫开始行动
                Witch.start();
                //  女巫用药
                Witch witchPlayer = null;
                if (!roleList.stream().filter(item -> Objects.equals(item.name, "女巫")).collect(Collectors.toList()).isEmpty()) {
                    witchPlayer = (Witch) roleList.stream().filter(item -> Objects.equals(item.name, "女巫")).collect(Collectors.toList()).get(0);
                    //  解药
                    if (witchPlayer.save()) {
                        isWitchSave = true;
                        operationList.add("第" + day + "天夜晚，女巫对 " + killedRole.id + " 号玩家（" + killedRole.name + "）使用解药。");
                    } else {
                        //  如果没有使用解药，是否使用毒药
                        poisonedRole = witchPlayer.kill(roleList);
                        if (poisonedRole != null) {
                            operationList.add("第" + day + "天夜晚，女巫对 " + poisonedRole.id + " 号玩家（" + poisonedRole.name + "）使用毒药。");
                            deathList.add(poisonedRole);
                        }
                    }
                }
                //  女巫结束行动
                Witch.end();
            }

            if (!roleList.stream().filter(item -> item.name == "守卫").collect(Collectors.toList()).isEmpty()) {
                //  守卫开始行动
                Guard.start();
                //  守卫守护
                Guard guardPlayer = null;
                if (!roleList.stream().filter(item -> Objects.equals(item.name, "守卫")).collect(Collectors.toList()).isEmpty()) {
                    guardPlayer = (Guard) roleList.stream().filter(item -> Objects.equals(item.name, "守卫")).collect(Collectors.toList()).get(0);
                    if (guardPlayer.isAlive) {
                        protectedRole = guardPlayer.protect(roleList, killedRole);
                        operationList.add("第" + day + "天夜晚，守卫对 " + protectedRole.id + " 号玩家（" + protectedRole.name + "）进行守护。");
                    }
                }
                //  守卫结束行动
                Guard.end();
            }

            System.out.println("\n等待天亮中...");
            TimerUtils.timer();

            //  更新所有身份的存活状态
            System.out.println("天亮了，今晚...");
            TimerUtils.timer();
            //  判断被守护和被解药的是否是同一人，若是，同守同救而死。
            if (isWitchSave) {
                deathList.remove(killedRole);
            }
            if (isWitchSave && killedRole == protectedRole) {
                deathList.add(killedRole);
            } else if (protectedRole == killedRole) {
                deathList.remove(protectedRole);
            }

            //  上帝宣布死亡/平安夜
            if (deathList.isEmpty()) {
                System.out.println("是平安夜");
            } else {
                int finalDay = day;
                deathList.forEach(item -> {
                    operationList.add("第" + finalDay + "天白天，" + item.id + " 号玩家（" + item.name + "）死亡。");
                    System.out.println(item.id + "号 死亡");
                    item.dead();
                });
                TimerUtils.timer();
                deathList.stream().filter(item -> (item.name == "猎人" || item.name == "狼王") && !item.isAlive).collect(Collectors.toList()).forEach(item -> {
                    Role doubleKilledRole = null;
                    try {
                        doubleKilledRole = item.deadKill(roleList);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    operationList.add("第" + finalDay + "天白天，" + item.id + " 号玩家（" + item.name + "）发动技能杀死 " + doubleKilledRole.id + " 号玩家（" + doubleKilledRole.name + "）。");
                    System.out.println(doubleKilledRole.id + "号 死亡");
                    doubleKilledRole.dead();
                    //  带走人之后要进行判断
                    if (gameState() != 0) return;
                });
            }
            System.out.println("\n==================================");

            /*
             * 白天开始发言（随机选一个人开始顺序发言）
             */
            List<Role> sayList = roleList.stream().filter(item -> item.isAlive).collect(Collectors.toList());
            int startIndex = new Random().nextInt(sayList.size());
            List<Role> sortedCircularList = new ArrayList<>();
            for (int i = 0; i < sayList.size(); i++) {
                sortedCircularList.add(sayList.get((startIndex + i) % sayList.size()));
            }
            sortedCircularList.forEach(item -> {
                item.say();
                try {
                    TimerUtils.timer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.println("发言完毕，开始投票...");
            TimerUtils.timer();

            /*
             * 投票
             */
            int temp = RandomUtils.getRandomNumber();
            Role votedRole = null;
            List<Role> voteList = null;
            if (temp <= 50) {
                voteList = roleList.stream().filter(item -> item.isAlive && item.camp).collect(Collectors.toList());
            } else {
                voteList = roleList.stream().filter(item -> item.isAlive && !item.camp).collect(Collectors.toList());
            }
            if (!voteList.isEmpty()) {
                votedRole = voteList.get(new Random().nextInt(voteList.size()));
                votedRole.dead();
                System.out.print(votedRole.id + "号玩家被投票出局，");
                operationList.add("第" + day + "天白天，" + votedRole.id + " 号玩家（" + votedRole.name + "）被投票出局。");
                if (votedRole.name == "猎人" || votedRole.name == "狼王") {
                    Role doubleKilledRole = null;
                    try {
                        doubleKilledRole = votedRole.deadKill(roleList);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    operationList.add("第" + day + "天白天，" + votedRole.id + " 号玩家（" + votedRole.name + "）发动技能杀死 " + doubleKilledRole.id + " 号玩家（" + doubleKilledRole.name + "）。");
                    System.out.println(doubleKilledRole.id + "号 死亡");
                    doubleKilledRole.dead();
                    //  带走人之后要进行判断
                    if (gameState() != 0) return;
                }
            }

            //  ====  判断游戏是否结束  ====
            if (gameState() != 0) return;
        }
    }

    /**
     * 游戏状态
     *
     * @return 0 代表未结束，1代表狼人阵营胜利，2代表好人阵营胜利
     */
    private Integer gameState() {
        List<Role> List1 = roleList.stream().filter(item -> item.camp && item.isAlive).collect(Collectors.toList());
        List<Role> List2 = roleList.stream().filter(item -> !item.camp && item.isAlive).collect(Collectors.toList());
        List<Role> civilianList = roleList.stream().filter(item -> Objects.equals(item.name, "平民") && item.isAlive).collect(Collectors.toList());
        if (List1.isEmpty()) {
            System.out.println("游戏结束，好人获胜。");
            return 2; //  狼人阵营全死，好人胜利
        } else if (List2.isEmpty()) {
            System.out.println("游戏结束，狼人获胜。");
            return 1; //  好人阵营全死，狼人胜利
        } else if (civilianList.isEmpty()) {
            System.out.println("游戏结束，狼人获胜。");
            return 1; //  平民全死，狼人胜利
        } else if (List1.toArray().length >= List2.toArray().length) {
            System.out.println("游戏结束，狼人获胜。");
            return 1; //  狼人阵营人数等于或超过好人阵营人数，狼人胜利
        }
        System.out.println("游戏继续。");
        return 0; //  游戏未结束
    }

    /**
     * 结算游戏
     */
    private void gameSettlement() {
        System.out.print("==================================\n输入 \"0\" 查看战绩：");
        Scanner scanner = new Scanner(System.in);
        int op = scanner.nextInt();
        if (op == 0) {
            System.out.println("==================================");
            roleList.forEach(item -> System.out.println("| " + item + " |"));
            System.out.println("==================================\n");
            operationList.forEach(System.out::println);
            System.out.println("\n==================================\n");
        }
    }
}
