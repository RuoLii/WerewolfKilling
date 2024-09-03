package com.ruoli.game.roles;

import com.ruoli.game.Utils.TimerUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 狼人：除了女巫毒杀、猎人枪杀外，在白天宣布死亡后，可带走场上任意一人，带走人无遗言。技能发动后再发动遗言。
 *
 * @author RuoLi
 * @version v1.0
 */
public class WolfKing extends Role {
    public WolfKing(Integer id) {
        super(id);
        this.name = "狼王";
        this.camp = true;
    }

    /**
     * 狼王死亡时带走一个人
     *
     * @param roleList 身份列表
     * @return 带走的人
     */
    @Override
    public Role deadKill(List<Role> roleList) throws InterruptedException {
        Role killedRole;
        System.out.println("狼王死亡，请选择杀死一人...");
        TimerUtils.timer();
        List<Role> aliveRoleList = roleList.stream().filter(item -> item.isAlive).collect(Collectors.toList());
        killedRole = aliveRoleList.get(new Random().nextInt(aliveRoleList.size()));
        System.out.println("狼王临死前杀死了" + killedRole.id + " 号玩家。");
        return aliveRoleList.get(new Random().nextInt(aliveRoleList.size()));
    }
}
