package com.kj.tdd.tddTest.exception;

import lombok.Data;

@Data
public class TddException extends RuntimeException {

    private String code;
    private String msg;

    public TddException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public TddException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public TddException(String msg, Throwable throwable) {
        super(msg, throwable);
        this.msg = msg;
    }

    public TddException(String code, String msg, Throwable throwable) {
        super(msg, throwable);
        this.code = code;
        this.msg = msg;
    }

}
