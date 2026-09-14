package com.sprint.hrbank.controller;

import com.sprint.hrbank.dto.BackUpDTO;
import com.sprint.hrbank.entity.BackUp;
import com.sprint.hrbank.service.BackUpRegister;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BackUpController {
    private final BackUpRegister backUpRegister;

    // 데이터 백업 목록 조회
//    @RequestMapping(method = RequestMethod.GET, value = "/api/backups")
//    public ResponseEntity<BackUpDTO> getBackUps() {
//    }

    // 데이터 백업 생성
    @RequestMapping(method = RequestMethod.POST, value = "/api/backups")
    public ResponseEntity<Void> createBackUp(HttpServletRequest request) {
        String workIp = request.getRemoteAddr();
        backUpRegister.executeBackUp(workIp);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    // 최근 백업 정보 조회
}
