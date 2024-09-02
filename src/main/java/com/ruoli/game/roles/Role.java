package com.ruoli.game.roles;

/**
 * 身份类
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

    public Role(Integer id) {
        this.id = id;
    }

    /**
     * 死亡事件
     * 某些身份死后会触发技能，这里方便子类重写该方法。
     */
    public void dead(){}

    /**
     * 遗言事件
     */
    public void sayLastWords() {
        System.out.println("我没有遗言");
    }
}
