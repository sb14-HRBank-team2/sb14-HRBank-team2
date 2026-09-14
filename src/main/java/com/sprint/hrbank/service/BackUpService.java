package com.sprint.hrbank.service;

import com.sprint.hrbank.dto.BackUpDTO;
import com.sprint.hrbank.entity.BackUp;
import com.sprint.hrbank.entity.Employee;
import com.sprint.hrbank.entity.File;
import com.sprint.hrbank.exception.CustomRuntimeException;
import com.sprint.hrbank.exception.ExceptionType;
import com.sprint.hrbank.repository.BackUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.sprint.hrbank.exception.ExceptionType.SERVER_ERROR;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BackUpService implements BackUpRegister{

    private final BackUpRepository backUpRepository;
    private final BackUpRegister backUpRegister;
    private final EmployeeFinder employeeFinder;

    @Transactional
    public void executeBackUp(String workIp) {
        // 1. 변경 이력이 있는지 체크 (데이터 수정 이력 부분 없음)
        // (직원 수정 이력 테이블 쪽)

        // 2. 직원 테이블의 모든 데이터 조회
        List<Employee> employees = employeeFinder.getAll();

        // 3. 직원 데이터를 csv 문자열로 변환
        StringBuilder csvEmployee = new StringBuilder();
        csvEmployee.append("ID, 직원번호, 이름, 이메일, 부서, 직급, 입사일, 상태");

        for (Employee emp : employees) {
            csvEmployee.append(emp.getId()).append(",")
                       .append(emp.getEmployeeNumber()).append(",")
                       .append(emp.getName()).append(",")
                       .append(emp.getEmail()).append(",")
                       .append(emp.getDepartment()).append(",")
                       .append(emp.getPosition()).append(",")
                       .append(emp.getHireDate()).append(",")
                       .append(emp.getStatus()).append("\n");
        }

        // 4. 로컬 디스크 또는 서버 저장 경로에 파일 물리적 저장
        try{
            String fileName = "employee_backup_" + "데이터 백업 이력 ID(나중에 호출)" + ".csv";
            String filePath = "/Users/codeit/Desktop/csv 테스트" + fileName;

            // 테스트 용
            // String temporaryBackupId = "999L";
            // String filePath = "employee_backup_" + temporaryBackupId + ".csv";

            // 5. 서버 내부에 저장
            Path path = Paths.get(filePath);
            Files.write(path, csvEmployee.toString().getBytes(StandardCharsets.UTF_8));

            // 5. File 테이블에 정보 저장
            int Filesize = csvEmployee.toString().getBytes(StandardCharsets.UTF_8).length;
            File fileEntity = File.create(
                    fileName,
                    "csv",
                    (long)Filesize,
                    filePath
            );

            // 6. 백업 이력 완료 처리 및 파일 매핑 (이쪽 부분 아직 없음)
            // BackUpList backUpList = BackUpList.create(fileEntity); // 데이터 백업 이력 생성
            // backUpList.changeTime(); // 데이터 백업 이력 테이블에 종료시간 필드가 갱신됨
            // backUpListRegister.save(backUpList); // 리파지토리에 저장
        } catch (IOException e) {
            throw new CustomRuntimeException(ExceptionType.SERVER_ERROR, "CSV 파일 저장 실패");
        }
    }

}