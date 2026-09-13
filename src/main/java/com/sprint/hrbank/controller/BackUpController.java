package com.sprint.hrbank.controller;

import com.sprint.hrbank.service.BackUpRegister;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BackUpController {
    private final BackUpRegister backUpRegister;

    @RequestMapping(method = RequestMethod.POST, value = "/api/backups")
    public ResponseEntity<Resource> createBackUp() {
        // 1. 직원 서비스에게 직원 정보들을 바이트 데이터로 받음
        byte[] fileData = backUpRegister.createBackUp();

        // employee_backup_740_20260912_04
        String employeeNumber = ;

        // 2. 바이트 데이터 + HttpHeader
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; ");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8");
    }

}
