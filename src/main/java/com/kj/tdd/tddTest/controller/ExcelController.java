package com.kj.tdd.tddTest.controller;

import com.kj.tdd.tddTest.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/importExcel")
    public void importExcel(HttpServletRequest request) {
        excelService.importExcel(request);
    }

    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) {
        excelService.exportExcel(response);
    }

}
