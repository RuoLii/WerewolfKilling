package com.ruoli.game.roles;

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
    public Witch(Integer id) {
        super(id);
        this.name = "女巫";
        this.camp = false;
    }
}
