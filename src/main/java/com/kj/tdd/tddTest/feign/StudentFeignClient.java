package com.kj.tdd.tddTest.feign;

import com.kj.tdd.tddTest.common.ResultDTO;
import com.kj.tdd.tddTest.domain.Student;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "eurekaClient", path = "/student", fallbackFactory = StudentFeignClientFallback.class)
public interface StudentFeignClient {

    @GetMapping(value = "/getStudent")
    ResultDTO<Student> getStudent(@ApiParam(value = "学生id") @RequestParam("id") Long id);

    @DeleteMapping(value = "/deleteStudent/{id}")
    ResultDTO deleteStudent(@ApiParam(value = "学生id") @PathVariable("id") Long id);

}
