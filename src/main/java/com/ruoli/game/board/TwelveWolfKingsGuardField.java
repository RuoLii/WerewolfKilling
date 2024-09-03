package com.ruoli.game.board;

import com.ruoli.game.Utils.TimerUtils;
import com.ruoli.game.roles.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 12人狼王守卫场
 *
 * @author RuoLi
 * @version v1.0
 */
public class TwelveWolfKingsGuardField {
    //  角色列表
    private static final List<Role> roleList = new ArrayList<>();
    //  所有游戏过程中产生的信息存放在这里，游戏后可查看
    private static final List<String> operationList = new ArrayList<>();

    public TwelveWolfKingsGuardField() throws InterruptedException {
        //  ====  分配角色及号码牌  ====
        //  4平民
        for (int i = 0; i < 4; i++) roleList.add(new Civilian(0));
        //  1预言家
        roleList.add(new Prophet(0));
        //  1女巫
        roleList.add(new Witch(0));
        //  1猎人
        roleList.add(new Hunter(0));
        //  1守卫
        roleList.add(new Guard(0));
        //  3狼人
        for (int i = 0; i < 3; i++) roleList.add(new Werewolf(0));
        //  1狼王
        roleList.add(new WolfKing(0));
        //  身份洗牌
        Collections.shuffle(roleList);
        int number = 1;
        for (Role role : roleList) {
            role.id = number++;
        }

        Role userRole = roleList.get(new Random().nextInt(11) + 1);

        //  为当前用户随机抽取一个身份
        System.out.println("\n----------------------------------\n\n你的身份是：" + userRole.name + "，号码为：" + userRole.id + "\n\n----------------------------------\n");
        TimerUtils.timer();
        System.out.println("**********************************\niwei：“游戏即将开始，请各位玩家做好准备！！”\n**********************************");

        //  ====  开始游戏逻辑(夜晚 ——> 白天 为一次循环)  ====
        int day = 0; //  进行了几晚
        List<Role> deathList = new ArrayList<>();
        Role protectedRole = null;
        Boolean isWitchSave = false;
        Role poisonedRole = null;
        while (gameState() == 0) {
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
            Role killedRole = Role.werewolfKill(roleList);
            operationList.add("第" + day + "天夜晚，狼人阵营向 " + killedRole.id + " 号玩家（" + killedRole.name + "）出刀。");
            deathList.add(killedRole);
            //  狼人/狼王结束行动
            Werewolf.end();

            //  预言家开始行动
            Prophet.start();
            //  预言家验人
            Prophet.check();
            //  预言家结束行动
            Prophet.end();

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

            //  守卫
            Guard.start();
            //  守卫守护逻辑
            Guard guardPlayer = null;
            if (!roleList.stream().filter(item -> Objects.equals(item.name, "守卫")).collect(Collectors.toList()).isEmpty()) {
                guardPlayer = (Guard) roleList.stream().filter(item -> Objects.equals(item.name, "守卫")).collect(Collectors.toList()).get(0);
                if (guardPlayer.isAlive) {
                    protectedRole = guardPlayer.protect(roleList, killedRole);
                    operationList.add("第" + day + "天夜晚，守卫对 " + protectedRole.id + " 号玩家（" + protectedRole.name + "）进行守护。");
                }
            }
            Guard.end();

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
                    try {
                        item.dead(roleList);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

            }
            System.out.println("\n==================================");

            /**
             * 白天开始发言
             */
            System.out.println("发言完毕，开始投票...");
            TimerUtils.timer();

            /**
             * 投票
             */
            int temp = new Random().nextInt(100) + 1;
            Role votedRole = null;
            List<Role> voteList = null;
            if (temp <= 50) {
                voteList = roleList.stream().filter(item -> item.isAlive && item.camp).collect(Collectors.toList());
            } else {
                voteList = roleList.stream().filter(item -> item.isAlive && !item.camp).collect(Collectors.toList());
            }
            votedRole = voteList.get(new Random().nextInt(voteList.toArray().length));
            votedRole.dead(roleList);
            System.out.print(votedRole.id + "号玩家被投票出局，");
            operationList.add("第" + day + "天白天，" + votedRole.id + " 号玩家（" + votedRole.name + "）被投票出局。");

            /**
             * 判断游戏是否结束
             */
            if (gameState() == 1) {
                System.out.println("游戏结束，狼人获胜。");
                break;
            } else if (gameState() == 2) {
                System.out.println("游戏结束，好人获胜。");
                break;
            } else {
                System.out.println("游戏继续。");
            }
            TimerUtils.timer();
        }

        System.out.print("输入 \"0\" 查看战绩：");
        Scanner scanner = new Scanner(System.in);
        int op = scanner.nextInt();
        if (op == 0) {
            System.out.println("\n\n==================================\n");
            roleList.forEach(item -> System.out.println(item.id + "号——" + item.name));
            System.out.println("\n\n==================================\n");
            operationList.forEach(System.out::println);
            System.out.println("\n==================================\n");
        }
    }

    /**
     * 判断游戏是否结束
     * @return 0 代表未结束，1代表狼人阵营胜利，2代表好人阵营胜利
     */
    private static Integer gameState() {
        List<Role> List1 = roleList.stream().filter(item -> item.camp && item.isAlive).collect(Collectors.toList());
        List<Role> List2 = roleList.stream().filter(item -> !item.camp && item.isAlive).collect(Collectors.toList());
        List<Role> civilianList = roleList.stream().filter(item -> Objects.equals(item.name, "平民") && item.isAlive).collect(Collectors.toList());
        if (List1.isEmpty()) {
            return 2; //  狼人阵营全死，好人胜利
        } else if (List2.isEmpty()) {
            return 1; //  好人阵营全死，狼人胜利
        } else if (civilianList.isEmpty()) {
            return 1; //  平民全死，狼人胜利
        } else if (List1.toArray().length >= List2.toArray().length) {
            return 1; //  狼人阵营人数等于或超过好人阵营人数，狼人胜利
        }
        return 0; //  游戏未结束
    }
}
