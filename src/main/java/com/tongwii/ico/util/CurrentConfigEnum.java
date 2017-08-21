package com.tongwii.ico.util;

/**
 * 当前配置环境枚举类
 *
 * @author Zeral
 * @date 2017-08-11
 */
public enum CurrentConfigEnum {
    dev,                 //开发环境，提供全部日志输出，忽略token验证
    pro,                  //产品环境
    test;                       //测试环境

    CurrentConfigEnum() {}

    @Override
    public String toString() {
        return this.name();
    }
}
