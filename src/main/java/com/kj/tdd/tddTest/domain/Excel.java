package com.kj.tdd.tddTest.domain;

import com.kj.tdd.tddTest.utils.ExcelAnnotation;
import lombok.Data;

import java.util.Date;

@Data
public class Excel {

    private Long id;

    @ExcelAnnotation(columnIndex = 1, columnName = "姓名")
    private String name;

    @ExcelAnnotation(columnIndex = 2, columnName = "年龄")
    private Integer age;

    @ExcelAnnotation(columnIndex = 3, columnName = "身高")
    private Double height;

    @ExcelAnnotation(columnIndex = 4, columnName = "体重")
    private Double weight;

    @ExcelAnnotation(columnIndex = 5, columnName = "学历")
    private String edu;

    private Date createTime;

    private Date updateTime;

    private Integer status;

}