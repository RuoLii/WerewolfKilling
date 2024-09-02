package com.ruoli.game.roles;

/**
 * 猎人：除了女巫毒杀外，在白天宣布死亡后，可带走场上任意一人，带走人无遗言。技能发动后再发动遗言。
 * @author RuoLi
 * @version v1.0
 */
public class Hunter extends Role{
    public Hunter(Integer id) {
        super(id);
        this.name = "猎人";
        this.camp = false;
    }
}
