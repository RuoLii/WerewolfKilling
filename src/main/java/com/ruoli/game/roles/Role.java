package com.ruoli.game.roles;

import com.ruoli.game.Utils.RandomUtils;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 身份类
 *
 * @author RuoLi
 * @version v1.0
 */
public class Role {
    //  分配号码
    public Integer id;
    //  身份名
    public String name;
    //  阵营，true为狼人阵营，false为好人阵营
    public Boolean camp;
    //  是否存活
    public Boolean isAlive;

    public Role(Integer id) {
        this.id = id;
        this.isAlive = true;
    }

    /**
     *
     */
    public void say() {
        System.out.println(id + " 号玩家发言...");
    }

    /**
     * 死亡事件
     */
    public void dead() {
        this.isAlive = false;
    }

    /**
     * 死亡杀人
     * 某些身份死后会触发技能，这里方便子类重写该方法。
     */
    public Role deadKill(List<Role> roleList) throws InterruptedException {
        return null;
    }

    /**
     * 遗言事件
     */
    public void sayLastWords() {
        System.out.println("这是 " + this.id + " 号玩家的遗言...");
    }

    /**
     * 投票事件
     *
     * @param roleList 身份列表
     * @return 返回一个号码
     */
    public Integer vote(List<Role> roleList) {
        return 0;
    }

    /**
     * 狼人杀人事件：
     * 传入一个游戏的角色列表
     * 较大概率投好人，小概率狼人自刀
     *
     * @return 杀人事件处理后，将杀人对象返回
     */
    public static Role werewolfKill(List<Role> roleList) {
        int temp = RandomUtils.getRandomNumber();
        List<Role> selectList;
        if (temp <= 95) {
            selectList = roleList.stream().filter(item -> !item.camp && item.isAlive).collect(Collectors.toList());
        } else {
            selectList = roleList.stream().filter(item -> item.camp && item.isAlive).collect(Collectors.toList());
        }
        return selectList.get(new Random().nextInt(selectList.size()));
    }

    @Override
    public String toString() {
        String temp;
        if (isAlive) temp = "存活";
        else temp = "死亡";
        return id + "号—" + name + "—" + temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, ((Role) o).id) && Objects.equals(name, ((Role) o).name);
    }
}
