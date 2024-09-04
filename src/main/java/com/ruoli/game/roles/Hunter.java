package com.ruoli.game.roles;

import com.ruoli.game.Utils.TimerUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 猎人：除了女巫毒杀外，在白天宣布死亡后，可带走场上任意一人，带走人无遗言。技能发动后再发动遗言。
 *
 * @author RuoLi
 * @version v1.0
 */
public class Hunter extends Role {
    public Hunter(Integer id) {
        super(id);
        this.name = "猎人";
        this.camp = false;
    }

    /**
     * 猎人死亡时带走一个人
     * @param roleList 身份列表
     * @return 带走的人
     */
    @Override
    public Role deadKill(List<Role> roleList) throws InterruptedException {
        Role killedRole;
        System.out.println("猎人死亡，请选择带走一人...");
        TimerUtils.timer();
        List<Role> aliveRoleList = roleList.stream().filter(item -> item.isAlive).collect(Collectors.toList());
        killedRole = aliveRoleList.get(new Random().nextInt(aliveRoleList.size()));
        System.out.println("猎人开枪带走了" + killedRole.id + " 号玩家。");
        return aliveRoleList.get(new Random().nextInt(aliveRoleList.size()));
    }
}
