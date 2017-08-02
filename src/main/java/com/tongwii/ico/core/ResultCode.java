package com.tongwii.ico.core;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
    SUCCESS(200, "请求成功"),//成功
    ERROR(500, "请求出错"),//服务器内部错误
    FAIL(400, "请求失败"),//失败
    UNAUTHORIZED(401, "身份验证失败"),//未认证（签名错误）
    NOT_FOUND(404, "请求页面不存在"),//接口不存在
    SERVICE_UNAVAILABLE(503, "由于临时的服务器维护或者过载,服务器当前无法处理请求"),
    REQUEST_TIME_OUT(408, "服务器等待客户端发送的请求时间过长,超时"),
    TOO_MANY_REQUESTS(429, "太多的请求" ),
    TRADE_REPETITION(460, "重复交易" );

    public int code;
    private String statusMessage;

    public String getStatusMessage () {
        return statusMessage;
    }

    public int getCode() {
        return code;
    }

    ResultCode(int code, String statusMessage) {
        this.code = code;
        this.statusMessage = statusMessage;
    }
}
