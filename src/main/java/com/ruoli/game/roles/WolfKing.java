package com.ruoli.game.roles;

/**
 * 狼人：除了女巫毒杀、猎人枪杀外，在白天宣布死亡后，可带走场上任意一人，带走人无遗言。技能发动后再发动遗言。
 * @author RuoLi
 * @version v1.0
 */
public class WolfKing extends Role{
    public WolfKing(Integer id) {
        super(id);
        this.name = "狼王";
        this.camp = true;
    }
}
