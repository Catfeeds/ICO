package com.tongwii.ico.util;

/**
 * 当前配置环境枚举类
 *
 * @author Zeral
 * @date 2017-08-11
 */
public enum CurrentConfig {
    DEVELOPMENT("dev"),                 //开发环境，提供全部日志输出，忽略token验证
    PRODUCTION("pro"),                  //产品环境
    TEST("test");                       //测试环境

    private String config;

    CurrentConfig(String config) {
        this.config = config;
    }
}
