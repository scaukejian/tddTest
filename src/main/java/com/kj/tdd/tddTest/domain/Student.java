package com.kj.tdd.tddTest.domain;

import com.kj.tdd.tddTest.utils.ExcelAnnotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class Student {

    private static final String TAG = "TEST STUDENT";

    @ExcelAnnotation(columnIndex = 1, columnName = "ID")
    private Long id;

    @ExcelAnnotation(columnIndex = 2, columnName = "名称")
    private String name;

    private String sex;

    private int age;

    public Student() {}

    private Student(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private String printStudentInfo(String name) {
        String msg = "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';

        System.out.println("student info:" + msg);
        return msg;
    }
}
