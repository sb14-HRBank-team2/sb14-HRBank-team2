package com.sprint.hrbank.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.hrbank.application.backup.BackupCommandService;
import com.sprint.hrbank.application.backup.BackupQueryService;
import com.sprint.hrbank.application.backup.CSVService;
import com.sprint.hrbank.application.backup.dto.BackupDto;
import com.sprint.hrbank.application.backup.provided.command.CSVCreator;
import com.sprint.hrbank.application.backup.required.BackupRepository;
import com.sprint.hrbank.application.department.required.DepartmentRepository;
import com.sprint.hrbank.application.employee.required.EmployeeRepository;
import com.sprint.hrbank.application.fileinfo.FileInfoService;
import com.sprint.hrbank.application.fileinfo.required.FileInfoRepository;
import com.sprint.hrbank.common.config.QuerydslConfig;
import com.sprint.hrbank.domain.backup.Backup;
import com.sprint.hrbank.domain.backup.BackupStatus;
import com.sprint.hrbank.domain.department.Department;
import com.sprint.hrbank.domain.employee.Employee;
import com.sprint.hrbank.domain.fileinfo.FileInfo;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(
    properties = {
      "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
      "spring.jpa.hibernate.ddl-auto=create-drop",
      "spring.data.jpa.repositories.bootstrap-mode=lazy"
    })
@Import({
  BackupCommandService.class,
  BackupQueryService.class,
  CSVService.class,
  FileInfoService.class,
  QuerydslConfig.class
})
@Transactional
class BackupCommandServiceTest {

  @Autowired private BackupCommandService backupCommandService;

  @Autowired private BackupQueryService backupQueryService;

  @Autowired private BackupRepository backupRepository;
  @Autowired private CSVCreator csvCreator;
  @Autowired private DepartmentRepository departmentRepository;
  @Autowired private EmployeeRepository employeeRepository;
  @Autowired private FileInfoRepository fileInfoRepository;

  @TempDir Path directory;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(csvCreator, "fileDirectory", directory.toString());
  }

  @Test
  @DisplayName("백업 이력과 CSV 메타데이터 저장")
  void createBackup() throws Exception {
    Department department =
        departmentRepository.save(Department.create("백엔드", "백엔드", LocalDate.of(2026, 9, 13)));
    Employee employee =
        employeeRepository.save(
            Employee.create(
                department, null, "김어진", "kim@naver.com", "대리", LocalDate.of(2026, 9, 14)));
    employeeRepository.flush();

    BackupDto result = backupCommandService.create("ip주소");

    Backup saved = backupRepository.findById(result.id()).orElseThrow();
    FileInfo fileInfo = fileInfoRepository.findById(result.fileId()).orElseThrow();

    assertThat(result.worker()).isEqualTo("ip주소");
    assertThat(result.status()).isEqualTo(BackupStatus.COMPLETED);
    assertThat(result.startedAt()).isNotNull();
    assertThat(result.endedAt()).isNotNull();
    assertThat(result.fileId()).isNotNull();
    assertThat(saved.getStatus()).isEqualTo(BackupStatus.COMPLETED);
    assertThat(saved.getWorker()).isEqualTo("ip주소");
    assertThat(fileInfo.getName()).startsWith("employee_backup_");
    assertThat(Files.readAllLines(directory.resolve(fileInfo.getName())))
        .containsExactly(
            "ID,직원번호,이름,이메일,부서,직급,입사일,상태",
            employee.getId()
                + ","
                + employee.getEmployeeNumber()
                + ",김어진,kim@naver.com,백엔드,대리,2026-09-14,ACTIVE");
  }

  @Test
  @DisplayName("진행중인 최신백업 이력조회")
  void getLatestBackup() {
    BackupDto created = backupCommandService.create("ip주소");

    BackupDto result = backupQueryService.getLatestBackupByStatus(BackupStatus.COMPLETED);

    assertThat(result.id()).isEqualTo(created.id());
    assertThat(result.status()).isEqualTo(BackupStatus.COMPLETED);
    assertThat(result.worker()).isEqualTo("ip주소");
  }
}
