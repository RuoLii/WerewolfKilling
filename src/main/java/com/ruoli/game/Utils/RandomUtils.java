package com.ruoli.game.Utils;

import java.util.Random;

public class RandomUtils {

    private static final Random RANDOM = new Random();

    /**
     * @return 随机返回一个 1 - 100 的数字
     */
    public static Integer getRandomNumber() {
        return RANDOM.nextInt( 100) + 1;
    }
}