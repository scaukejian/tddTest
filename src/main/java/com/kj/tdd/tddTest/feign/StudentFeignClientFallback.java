package com.kj.tdd.tddTest.feign;

import com.kj.tdd.tddTest.common.ResultDTO;
import com.kj.tdd.tddTest.domain.Student;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StudentFeignClientFallback implements FallbackFactory<StudentFeignClient> {

    @Override
    public StudentFeignClient create(Throwable cause) {
        return new StudentFeignClient() {
            @Override
            public ResultDTO<Student> getStudent(Long id) {
                log.error("访问getStudent失败：{}, id:{}", cause.getMessage(), id.toString());
                return ResultDTO.error();
            }

            @Override
            public ResultDTO deleteStudent(Long id) {
                log.error("访问deleteStudent失败：{}, id:{}", cause.getMessage(), id.toString());
                return ResultDTO.error();
            }
        };
    }
}
