package com.ruoli.game.roles;

import com.ruoli.game.Utils.TimerUtils;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 预言家：晚上可验人
 * @author RuoLi
 * @version v1.0
 */
public class Prophet extends Role{
    public Prophet(Integer id) {
        super(id);
        this.name = "预言家";
        this.camp = false;
    }

    public static Role check(List<Role> roleList) throws InterruptedException {
        System.out.println("TA的身份是...");
        TimerUtils.timer();
        List<Role> checkRoleList = roleList.stream().filter(item -> item.name != "预言家").collect(Collectors.toList());
        return checkRoleList.get(new Random().nextInt(checkRoleList.size()));
    }

    public static void start() throws InterruptedException {
        System.out.println("\n\n预言家请睁眼...");
        TimerUtils.timer();
        System.out.println("预言家请选择你要查验的对象。");
        TimerUtils.timer();
    }

    public static void end() throws InterruptedException {
        System.out.println("预言家请闭眼。\n\n==================================");
        TimerUtils.timer();
    }
}
