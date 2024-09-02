package com.ruoli.game.roles;

/**
 * 平民：无特殊技能，白天可发言。
 * @author RuoLi
 * @version v1.0
 */
public class Civilian extends Role{
    public Civilian(Integer id) {
        super(id);
        this.name = "平民";
        this.camp = false;
    }
}
