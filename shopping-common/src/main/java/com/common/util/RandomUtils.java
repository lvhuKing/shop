package com.common.util;

/**
* @Description 作用: 生成随机数
* @Author ccl
* @CreateDate 2021/2/21 14:29
**/
public abstract class RandomUtils {

    private static final int BIT_WEIGHT = 10; // 位权大小

    private static final int MIN_LENGTH = 1;  // 最小长度

    private static final int MAX_LENGTH = 8;  // 最大长度

    /**
     * 随机生成指定长度的数字
     */
    public static int random(int length) {
        if (length < MIN_LENGTH) {
            length = MIN_LENGTH;
        }

        if (length > MAX_LENGTH) {
            length = MAX_LENGTH;
        }

        return (int) ((Math.random () * 9 + 1) * Math.pow (BIT_WEIGHT, length - 1));
    }

    /**
     * 随机生成指定长度的数字字符串
     */
    public static String randomString(int length) {
        if (length < MIN_LENGTH) {
            length = MIN_LENGTH;
        }

        if (length > MAX_LENGTH) {
            length = MAX_LENGTH;
        }

        return String.valueOf ((int) ((Math.random () * 9 + 1) * Math.pow (BIT_WEIGHT, length - 1)));
    }
}
