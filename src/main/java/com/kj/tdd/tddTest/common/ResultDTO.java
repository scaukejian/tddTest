package com.kj.tdd.tddTest.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultDTO<T> implements Serializable {

    private String code;

    private String msg;

    private T data;

    public ResultDTO() {}

    public ResultDTO(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultDTO ok() {
        return common("000000", "SUCCESS");
    }

    public static <T> ResultDTO ok(T data) {
        ResultDTO dto = common("000000", "SUCCESS");
        dto.data = data;
        return dto;
    }

    public static <T> ResultDTO ok(String code, String msg, T data) {
        ResultDTO dto = common(code, msg);
        dto.data = data;
        return dto;
    }

    public static <T> ResultDTO error() {
        return common("500", "未知错误，请联系管理员");
    }

    public static <T> ResultDTO error(String msg) {
        return common("500", msg);
    }

    public static <T> ResultDTO error(String code, String msg) {
        return common(code, msg);
    }

    public static <T> ResultDTO error(String code, String msg, T data) {
        ResultDTO resultDTO = common(code, msg);
        resultDTO.data = data;
        return resultDTO;
    }

    public static <T> ResultDTO common(String code, String msg) {
        ResultDTO resultDTO  = new ResultDTO<T>();
        resultDTO.setCode(code);
        resultDTO.setMsg(msg);
        return resultDTO;
    }
}
