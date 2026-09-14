package com.sprint.hrbank.application.backup;

import com.sprint.hrbank.application.employee.required.EmployeeRepository;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.employee.Employee;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CSVService {

  private final EmployeeRepository employeeRepository;

  @Value("${file.dir:/tmp/hrbank/files/}")
  private String fileDirectory;

  @Transactional(readOnly = true)
  public Path createCSV() {
    List<Employee> empList = employeeRepository.findAll(Sort.by("id"));
    String fileName = "employee_backup_" + UUID.randomUUID() + ".csv";
    Path path = Paths.get(fileDirectory, fileName);

    try {
      Files.createDirectories(path.getParent());

      try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
        writer.write("ID,직원번호,이름,이메일,부서,직급,입사일,상태");
        writer.newLine();

        for (Employee emp : empList) {
          writer.write(
              emp.getId()
                  + ","
                  + emp.getEmployeeNumber()
                  + ","
                  + emp.getName()
                  + ","
                  + emp.getEmail()
                  + ","
                  + emp.getDepartment().getName()
                  + ","
                  + emp.getPosition()
                  + ","
                  + emp.getHireDate()
                  + ","
                  + emp.getStatus());
          writer.newLine();
        }
      }
    } catch (IOException e) {
      throw new CustomRuntimeException(ExceptionType.BACKUP_FILE_EXEPTION);
    }

    return path;
  }
}
