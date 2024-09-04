package com.ruoli.game.board;

import com.ruoli.game.roles.*;

import java.util.Collections;

/**
 * 9人经典场，配置为预言家*1、女巫*1、猎人*1、平民*3、狼人*3
 */
public class NineClassicField extends NewGame{

    public NineClassicField() throws InterruptedException {}

    @Override
    protected void roleReady() {
        //  1预言家
        roleList.add(new Prophet(0));
        //  1女巫
        roleList.add(new Witch(0));
        //  1猎人
        roleList.add(new Hunter(0));
        //  3平民
        for (int i = 0; i < 3; i++) roleList.add(new Civilian(0));
        //  3狼人
        for (int i = 0; i < 3; i++) roleList.add(new Werewolf(0));

        //  身份洗牌
        Collections.shuffle(roleList);
        int number = 1;
        for (Role role : roleList) {
            role.id = number++;
        }
    }
}
