package com.tongwii.ico.exception;

/**
 * 用户已存在异常
 *
 * @author Zeral
 * @date 2017-08-04
 */
public class EmailExistException extends RuntimeException {

    public EmailExistException(String message) {
        super(message);
    }

    public EmailExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
