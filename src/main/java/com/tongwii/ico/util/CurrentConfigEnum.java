package com.tongwii.ico.util;

/**
 * 当前配置环境枚举类
 *
 * @author Zeral
 * @date 2017-08-11
 */
public enum CurrentConfigEnum {
    DEV,                 //开发环境，提供全部日志输出，忽略token验证
    PRO,                  //产品环境
    TEST;                       //测试环境

    CurrentConfigEnum() {}

    @Override
    public String toString() {
        return this.name();
    }
}
