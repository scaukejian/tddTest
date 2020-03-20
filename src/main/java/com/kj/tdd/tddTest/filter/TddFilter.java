package com.kj.tdd.tddTest.filter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kj.tdd.tddTest.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TddFilter implements Filter {

    /**
     * google提供的guava本地缓存
     */
    Cache<Long, Student> cache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(100, TimeUnit.SECONDS).build();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("==========开始===========");

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("==========执行中===========");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String id = req.getParameter("id");
        if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
            Long studentId = Long.parseLong(id);
            try {
                Student student = cache.get(studentId, () -> {
                    log.info("---------------------------------------------有执行啊-----------------------------------------");
                    Student student1 = new Student();
                    student1.setId(studentId);
                    student1.setName("测试");
                    return student1;
                });
                log.info("student:{}", student);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("==========结束===========");
    }
}
