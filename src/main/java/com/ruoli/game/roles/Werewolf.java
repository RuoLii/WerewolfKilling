package com.ruoli.game.roles;

import com.ruoli.game.Utils.TimerUtils;

/**
 * 狼人：夜晚可投票杀人，第一次平票后再次投票，第二次平票视为空刀。夜晚顺位最高。
 * @author RuoLi
 * @version v1.0
 */
public class Werewolf extends Role{
    public Werewolf(Integer id) {
        super(id);
        this.name = "狼人";
        this.camp = true;
    }

    public static void start(int day) throws InterruptedException {
        System.out.println("==================================\n\n狼人请睁眼...");
        TimerUtils.timer();
        if (day == 1) {
            System.out.println("狼人请相互确认身份。");
            TimerUtils.timer();
        }
        System.out.println("狼人请杀人，统一意见。");
    }

    public static void end() throws InterruptedException {
        TimerUtils.timer();
        System.out.println("狼人请闭眼。\n\n==================================");
        TimerUtils.timer();
    }
}
