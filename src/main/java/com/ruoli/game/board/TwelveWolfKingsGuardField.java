package com.ruoli.game.board;

import com.ruoli.game.roles.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 12人狼王守卫场
 *
 * @author RuoLi
 * @version v1.0
 */
public class TwelveWolfKingsGuardField {
    //  角色列表
    private static List<Role> roleList = new ArrayList<>();

    private static void timer() throws InterruptedException {
        Thread.sleep(1666);
    }

    /**
     * 判断游戏是否结束
     *
     * @return 0 代表未结束，1代表狼人阵营胜利，2代表好人阵营胜利
     */
    private static Integer gameState() {
        List<Role> List1 = roleList.stream().filter(item -> item.camp).collect(Collectors.toList());
        List<Role> List2 = roleList.stream().filter(item -> !item.camp).collect(Collectors.toList());
        List<Role> civilianList = roleList.stream().filter(item -> Objects.equals(item.name, "平民")).collect(Collectors.toList());
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
//            System.out.println(role);
        }

        Role userRole = roleList.get(new Random().nextInt(11) + 1);

        //  为当前用户随机抽取一个身份
        System.out.println("==================================\n\n你的身份是：" + userRole.name + "，号码为：" + userRole.id + "\n\n==================================\n\n");

        System.out.println("iwei：“游戏即将开始，请各位玩家做好准备！！”");

        //  ====  开始游戏逻辑(夜晚 ——> 白天 为一次循环)  ====
        int day = 1;
        while (gameState() == 0) {
            timer();
            System.out.println("天黑请闭眼...");
            timer();

            //  狼人
            System.out.println("狼人请睁眼...");
            timer();
            System.out.println("狼人请闭眼。");
            timer();

            //  预言家
            System.out.println("预言家请睁眼...");
            timer();
            System.out.println("预言家请闭眼。");
            timer();

            //  女巫
            System.out.println("女巫请睁眼...");
            timer();
            System.out.println("女巫请闭眼。");
            timer();

            //  守卫
            System.out.println("守卫请睁眼...");
            timer();
            System.out.println("守卫，今天晚上你要守谁？");
            timer();
            System.out.println("守卫请闭眼。");
            timer();

            System.out.println("等待天亮中...");
            timer();

            //  更新所有身份的存活状态，稍后进行游戏状态判断

            System.out.println("天亮了，今晚是。。。");

            /**
             * 白天开始发言
             * 死左或死右开始发言；若平安夜则由法官来决定发言的顺序
             */

            /**
             * 投票
             */

        }
    }
}
