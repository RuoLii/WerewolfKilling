package com.ruoli.game.roles;

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
}
