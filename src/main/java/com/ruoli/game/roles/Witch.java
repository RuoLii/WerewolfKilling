package com.ruoli.game.roles;

import com.ruoli.game.Utils.TimerUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 女巫：夜晚在狼人刀人后行动；
 * 一瓶毒药和解药，不可自救；
 * 拥有解药时可以预支狼刀；
 * 毒药无视守卫守护；
 * 不得同时使用两瓶药水。
 * @author RuoLi
 * @version v1.0
 */
public class Witch extends Role{
    private Boolean haveSave;
    private Boolean haveKill;

    public Witch(Integer id) {
        super(id);
        this.name = "女巫";
        this.camp = false;
        this.haveSave = true;
        this.haveKill = true;
    }

    /**
     *
     * @param roleList 身份列表
     * @return 空或者被解药的人
     */
    public Boolean save() {
        if (!haveSave) return false; //  没有药，救不了
        int temp = new Random().nextInt(100) + 1;
        if (temp <= 50) {
            haveSave = false;
            return true;
        }
        return false;
    }

    /**
     *
     * @param roleList 身份列表
     * @return 空或者被毒药的人
     */
    public Role kill(List<Role> roleList) {
        if (!haveKill) return null;
        int temp = new Random().nextInt(100) + 1;
        if (temp <= 50) {
            haveKill = false;
            List<Role> canKillList = roleList.stream().filter(item -> item.isAlive && item.id != id).collect(Collectors.toList());
            if (temp <= 15) {
                canKillList = canKillList.stream().filter(item -> item.camp).collect(Collectors.toList());
            }
            return canKillList.get(new Random().nextInt(canKillList.size()));
        }
        return null;
    }

    public static void start() throws InterruptedException {
        System.out.println("\n\n女巫请睁眼...");
        TimerUtils.timer();
        System.out.println("今晚，TA死了，你要使用解药吗？");
        TimerUtils.timer();
        System.out.println("要使用毒药吗？");
        TimerUtils.timer();
    }

    public static void end() throws InterruptedException {
        System.out.println("女巫请闭眼。\n\n==================================");
        TimerUtils.timer();
    }
}
