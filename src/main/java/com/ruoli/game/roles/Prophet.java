package com.ruoli.game.roles;

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
}
