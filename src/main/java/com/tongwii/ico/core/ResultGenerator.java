package com.tongwii.ico.core;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {

    /**
     * Gen success result result.
     *
     * @return the result
     */
    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS.getCode())
                .setMessage(ResultCode.SUCCESS.getStatusMessage());
    }

    /**
     * Gen success result result.
     *
     * @param data the data
     * @return the result
     */
    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS.getCode())
                .setMessage(ResultCode.SUCCESS.getStatusMessage())
                .setData(data);
    }

    /**
     * Gen fail result result.
     *
     * @param message the message
     * @return the result
     */
    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL.getCode())
                .setMessage(ResultCode.FAIL.getStatusMessage());
    }

    /**
     * Gen unauthorized result.
     *
     * @param message the message
     * @return the result
     */
    public static Result genUnauthorized (final String message ) {
        return new Result(ResultCode.UNAUTHORIZED.getCode(), message);
    }
}
