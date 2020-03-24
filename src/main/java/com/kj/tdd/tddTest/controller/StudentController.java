package com.kj.tdd.tddTest.controller;

import com.kj.tdd.tddTest.common.ResultDTO;
import com.kj.tdd.tddTest.domain.Student;
import com.kj.tdd.tddTest.feign.StudentFeignClient;
import com.kj.tdd.tddTest.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/student")
@Api(tags = "学生管理")
public class StudentController {

    @Autowired
    IStudentService studentService;
    @Autowired
    StudentFeignClient studentFeignClient;

    @PutMapping(value = "/addStudent")
    @ApiOperation(value = "新增学生")
    public ResultDTO<Student> addStudent(@RequestBody Student student) {
        student = studentService.addStudent(student);
        return ResultDTO.ok(student);
    }

    @PatchMapping(value = "/updateStudent")
    @ApiOperation("更新学生")
    public ResultDTO<Student> updateStudent(@RequestBody Student student) {
        student = studentService.updateStudent(student);
        return ResultDTO.ok(student);
    }

    @GetMapping(value = "/getStudent")
    @ApiOperation("获取学生信息")
    public ResultDTO<Student> getStudent(@ApiParam(value = "学生id") @RequestParam("id") Long id) {
        Student student = studentService.getStudent(id);
        return ResultDTO.ok(student);
    }

    @DeleteMapping(value = "/deleteStudent/{id}")
    @ApiOperation("删除学生")
    public ResultDTO deleteStudent(@ApiParam(value = "学生id") @PathVariable("id") Long id) {
        return studentFeignClient.deleteStudent(id);
    }

    @GetMapping("/listStudent")
    @ApiOperation("学生列表")
    public ResultDTO<List<Student>> listStudent(@ApiParam("分页大小") @RequestParam("pageSize") Long pageSize,
                                           @ApiParam("页码") @RequestParam("currentPage") Long currentPage) {
        List<Student> studentList = studentService.listStudent((currentPage - 1) * pageSize, pageSize);
        return ResultDTO.ok(studentList);
    }

}
