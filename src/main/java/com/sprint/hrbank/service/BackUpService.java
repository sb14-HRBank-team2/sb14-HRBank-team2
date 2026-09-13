package com.sprint.hrbank.service;

import com.sprint.hrbank.entity.Employee;
import com.sprint.hrbank.repository.BackUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BackUpService implements BackUpRegister {

    private final BackUpRepository backUpRepository;
    private final EmployeeFinder employeeFinder;

    @Transactional
    public byte[] createBackUp() {
        // + 직원 테이블에 직원이 수정되었는지 체크 (미구현)

        // 1. 자바 객체
        List<Employee> employees = employeeFinder.getAll();

        // 2. 문자열로 변환
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("ID, 직원번호, 이름, 이메일, 부서, 직급, 입사일, 상태");
        for (Employee emp : employees) {
            csvBuilder.append(emp.getId()).append(",")
                      .append(emp.getEmployeeNumber()).append(",")
                      .append(emp.getName()).append(",")
                      .append(emp.getEmail()).append(",")
                      .append(emp.getDepartment().getName()).append(",")
                      .append(emp.getPosition()).append(",")
                      .append(emp.getHireDate()).append(",")
                      .append(emp.getStatus()).append("\n");
        }
        String compStr = csvBuilder.toString();

        // 3. 바이트 형태로 변환 및 한글 깨짐 방지
        return compStr.getBytes(StandardCharsets.UTF_8);
    }
}