package com.kj.tdd.tddTest.service;

import com.kj.tdd.tddTest.aop.ControllerLogAspect;
import com.kj.tdd.tddTest.domain.Student;
import com.kj.tdd.tddTest.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    StudentMapper studentMapper;

    @Override
    public Student addStudent(Student student) {
        long flag = studentMapper.addStudent(student);
        log.info("flag:{}", flag);
        return student;
    }

    @Override
    public Student updateStudent(Student student) {
        long flag = studentMapper.updateStudent(student);
        log.info("flag:{}", flag);
        return student;
    }

    @Override
    public void deleteStudent(Long id) {
        long flag = studentMapper.deleteStudent(id);
        log.info("flag:{}", flag);
    }

    @Override
    public Student getStudent(Long id) {
        Long time = ControllerLogAspect.getStartTime().get();
        System.out.println("==========time:" +time);
        Student student = studentMapper.getStudent(id);
        log.info("student:{}", student);
        return student;
    }

    @Override
    public List<Student> listStudent(Long index, Long pageSize) {
        List<Student> studentList = studentMapper.listStudent(index, pageSize);
        log.info("studentList:{}", studentList);
        return studentList;
    }

    @Override
    public void printStudentInfo(Student student) {
        log.info("student info:{}", student);
    }

}
