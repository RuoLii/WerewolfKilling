package com.ruoli.game.board;

import com.ruoli.game.roles.*;

import java.util.Collections;

/**
 * 6人基础场，配置为预言家*1、女巫*1、平民*2、狼人*2
 */
public class SixBaseField extends NewGame{

    public SixBaseField() throws InterruptedException {}

    @Override
    protected void roleReady() {
        //  1预言家
        roleList.add(new Prophet(0));
        //  1女巫
        roleList.add(new Witch(0));
        //  2平民
        for (int i = 0; i < 4; i++) roleList.add(new Civilian(0));
        //  2狼人
        for (int i = 0; i < 2; i++) roleList.add(new Werewolf(0));

        //  身份洗牌
        Collections.shuffle(roleList);
        int number = 1;
        for (Role role : roleList) {
            role.id = number++;
        }
    }
}
