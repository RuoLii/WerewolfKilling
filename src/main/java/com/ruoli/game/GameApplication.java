package com.ruoli.game;

import com.ruoli.game.board.NineClassicField;
import com.ruoli.game.board.SixBaseField;
import com.ruoli.game.board.TwelveWolfKingsGuardField;

import java.util.Scanner;

public class GameApplication {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("======  欢迎游玩 iwei 狼人杀 ======\n");
        System.out.println("1. 6人标准场：预言家x1、女巫x1、平民x3、狼人x2。\n");
        System.out.println("2. 9人经典场：预言家x1、女巫x1、猎人x1、平民x3、狼人x3。\n");
        System.out.println("3. 12人狼王守卫场：预言家x1、女巫x1、猎人x1、守卫x1、平民x4、狼人x3、狼王x1。\n");
        System.out.println("==================================");
        System.out.print("请输入对应数字开始游戏：");
        Scanner in = new Scanner(System.in);
        int op = Integer.parseInt(in.next());
        switch(op) {
            case 1:
                new SixBaseField();
                break;
            case 2:
                new NineClassicField();
                break;
            case 3:
                new TwelveWolfKingsGuardField();
                break;
        }
    }
}
