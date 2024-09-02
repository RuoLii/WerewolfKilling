package com.ruoli.game.roles;

/**
 * 守卫：晚上守卫一个人（可以是自己），不能连续两晚选择同一个人，顺位比狼刀高
 * @author RuoLi
 * @version v1.0
 */
public class Guard extends Role{
    public Guard(Integer id) {
        super(id);
        this.name = "守卫";
        this.camp = false;
    }
}
