package com.kj.tdd.tddTest.aop;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Aspect
@Component
@Order(1)
@Slf4j
public class ControllerLogAspect {

    @Autowired
    private HttpServletRequest httpServletRequest;

    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();

    public static ThreadLocal<Long> getStartTime() {
        return startTime;
    }

    @Pointcut("execution(public * com.kj.tdd.tddTest.controller.*.*(..))")
    public void controllerLog() {

    }

    @Before("controllerLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.remove();
        startTime.set(System.currentTimeMillis());
        log.info("=========================请求开始=========================");
        log.info("[请求URL]:{}, {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURL());
        log.info("[请求IP]:{}", getIpAddress(httpServletRequest));
        log.info("[请求方法]:{}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        Map<String, Object> headerMap = new HashMap<>();
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String value = httpServletRequest.getHeader(headerName);
            headerMap.put(headerName, value);
        }
        // 下面两个数组中，参数值和参数名的个数和位置是一一对应的
        Object[] args = joinPoint.getArgs(); // 参数值
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames(); // 参数名
        TreeMap<String, Object> argMap = new TreeMap<>();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                //json序列化时需要过滤掉request和response类型参数
                if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse || arg instanceof BindingResult)
                    continue;
                argMap.put(argNames[i], arg);
            }
        }
        log.info("[请求头参数]:{}", JSONObject.toJSONString(headerMap));
        log.info("[请求参数]:{}", JSONObject.toJSONString(argMap));
    }

    @AfterReturning(returning = "response", pointcut = "controllerLog()")
    public void doAfter(Object response) {
        log.info("[返回数据]:{},耗时:{}ms", JSONObject.toJSONString(response), System.currentTimeMillis() - startTime.get());
    }

    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip == null ? "" : ip.split(",")[0];
    }
}
