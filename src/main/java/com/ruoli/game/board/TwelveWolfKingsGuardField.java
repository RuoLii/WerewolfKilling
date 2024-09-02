package com.ruoli.game.board;

import com.ruoli.game.roles.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 12人狼王守卫场
 *
 * @author RuoLi
 * @version v1.0
 */
public class TwelveWolfKingsGuardField {
    //  角色列表
    private static List<Role> roleList = new ArrayList<>();

    public TwelveWolfKingsGuardField() {
        //  ====  分配角色及号码牌  ====
        //  4平民
        for (int i = 0; i < 4; i++) roleList.add(new Civilian(0));
        //  1预言家
        roleList.add(new Prophet(0));
        //  1女巫
        roleList.add(new Witch(0));
        //  1猎人
        roleList.add(new Hunter(0));
        //  1守卫
        roleList.add(new Guard(0));
        //  3狼人
        for (int i = 0; i < 3; i++) roleList.add(new Werewolf(0));
        //  1狼王
        roleList.add(new WolfKing(0));

        Collections.shuffle(roleList);

        int number = 1;
        for (Role role : roleList) {
            role.id = number ++;
        }

        //  ====  开始游戏逻辑  ====

    }
}
