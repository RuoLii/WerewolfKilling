package com.ruoli.game.board;

import com.ruoli.game.roles.*;

import java.util.Collections;

/**
 * 12人狼王守卫场，配置为预言家*1、女巫*1、守卫*1、猎人*1、平民*4、狼人*3、狼王*1
 */
public class TwelveWolfKingsGuardField extends NewGame{

    public TwelveWolfKingsGuardField() throws InterruptedException {}

    @Override
    protected void roleReady() {
        //  1预言家
        roleList.add(new Prophet(0));
        //  1女巫
        roleList.add(new Witch(0));
        //  1守卫
        roleList.add(new Guard(0));
        //  1猎人
        roleList.add(new Hunter(0));
        //  4平民
        for (int i = 0; i < 4; i++) roleList.add(new Civilian(0));
        //  3狼人
        for (int i = 0; i < 3; i++) roleList.add(new Werewolf(0));
        //  1狼王
        roleList.add(new WolfKing(0));

        //  身份洗牌
        Collections.shuffle(roleList);
        int number = 1;
        for (Role role : roleList) {
            role.id = number++;
        }
    }
}
