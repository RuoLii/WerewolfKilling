package com.ruoli.game.roles;

import com.ruoli.game.Utils.RandomUtils;
import com.ruoli.game.Utils.TimerUtils;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 守卫：晚上守卫一个人（可以是自己），不能连续两晚选择同一个人，无法防女巫毒药
 *
 * @author RuoLi
 * @version v1.0
 */
public class Guard extends Role {
    //  守卫上回合守护的人的 id
    private Integer lastRoleId;

    public Guard(Integer id) {
        super(id);
        this.name = "守卫";
        this.camp = false;
        lastRoleId = 0;
    }

    /**
     * 守卫保护技能
     *
     * @param roleList 身份列表
     * @return 被守护的对象
     */
    public Role protect(List<Role> roleList, Role willKilledRole) {
        int randomNumber = RandomUtils.getRandomNumber();
        if (randomNumber <= 15 && willKilledRole.id != lastRoleId) {
            return willKilledRole;
        } else {
            List<Role> temp = roleList.stream().filter(item -> item.isAlive).collect(Collectors.toList());
            Role t = temp.get(new Random().nextInt(temp.size()));
            while (Objects.equals(lastRoleId, t.id)) {
                t = temp.get(new Random().nextInt(temp.size()));
            }
            lastRoleId = t.id;
            return t;
        }
    }

    public static void start() throws InterruptedException {
        System.out.println("\n\n守卫请睁眼...");
        TimerUtils.timer();
        System.out.println("守卫，今天晚上你要守谁？");
        TimerUtils.timer();
    }

    public static void end() throws InterruptedException {
        System.out.println("守卫请闭眼。\n\n==================================");
        TimerUtils.timer();
    }
}
