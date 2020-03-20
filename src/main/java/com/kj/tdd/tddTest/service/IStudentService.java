package com.kj.tdd.tddTest.service;

import com.kj.tdd.tddTest.domain.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.ResultMap;

import java.util.List;

public interface IStudentService {

    Student addStudent(Student student);

    Student updateStudent(Student student);

    void deleteStudent(Long id);

    Student getStudent(Long id);

    List<Student> listStudent(Long index, Long pageSize);

    void printStudentInfo(Student student);

}
