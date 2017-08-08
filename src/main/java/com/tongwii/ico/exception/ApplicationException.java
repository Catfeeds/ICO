package com.tongwii.ico.exception;

/**
 * 系统异常
 *
 * @author Zeral
 * @date 2017-08-06
 */
public class ApplicationException extends RuntimeException {
    /**
     * 构造方法
     *
     * @param arg0 信息
     * @param arg1 原因
     */
    public ApplicationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * 构造方法
     *
     * @param arg0 信息
     */
    public ApplicationException(String arg0) {
        super(arg0);
    }

    /**
     * 构造方法
     *
     * @param arg0 原因
     */
    public ApplicationException(Throwable arg0) {
        super(arg0);
    }
}
