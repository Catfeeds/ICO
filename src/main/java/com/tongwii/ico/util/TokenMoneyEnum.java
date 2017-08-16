package com.tongwii.ico.util;

/**
 * 代币英文简称枚举类
 *
 * @author Zeral
 * @date 2017-08-15
 */
public enum TokenMoneyEnum {
    ETH,
    BTC;

    TokenMoneyEnum() {
    }

    @Override
    public String toString() {
        return this.name();
    }
}
