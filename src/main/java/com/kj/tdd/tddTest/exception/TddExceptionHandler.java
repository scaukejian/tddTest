package com.kj.tdd.tddTest.exception;

import com.kj.tdd.tddTest.common.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class TddExceptionHandler {

    @ExceptionHandler(TddException.class)
    public ResultDTO handlerTddException(TddException e) {
        return ResultDTO.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResultDTO handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResultDTO.error("500", "未知错误，请联系系统管理员");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResultDTO handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return ResultDTO.error("501", "数据库中已存在该记录");
    }

    /**
     * 处理请求单个参数不满足校验规则的异常信息
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultDTO<Void> constraintViolationExceptionHandler(HttpServletRequest request,
                                                               ConstraintViolationException exception) {
        log.error(exception.getMessage(),exception);
        return ResultDTO.error("502", exception.getMessage());
    }

    /**
     * 参数无效的异常信息处理。
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResultDTO<Void> handleIllegalArgumentException(HttpServletRequest request,
                                                          IllegalArgumentException exception) {
        log.error(exception.getMessage(),exception);
        return ResultDTO.error("503", exception.getMessage());
    }


}
