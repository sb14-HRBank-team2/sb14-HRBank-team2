package com.sprint.hrbank.application.backup;

import com.sprint.hrbank.application.backup.provided.command.CSVCreator;
import com.sprint.hrbank.application.employee.provided.query.EmployeeEntityFinder;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.employee.Employee;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CSVService implements CSVCreator {

  private final EmployeeEntityFinder finder;

  @Value("${file.dir:/tmp/hrbank/files/}")
  private String fileDirectory;

  @Override
  public Path createCSV() {
    List<Employee> empList = finder.getAll(Sort.by("id"));
    String fileName =
        "employee_backup_" + LocalDateTime.now().toString().replace(":", "-") + ".csv";
    Path path = Paths.get(fileDirectory, fileName);

    try {
      Files.createDirectories(path.getParent());

      try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
        writer.write("ID,직원번호,이름,이메일,부서,직급,입사일,상태");
        writer.newLine();

        for (Employee emp : empList) {
          writer.write(
              String.join(
                  ",",
                  escape(emp.getId()),
                  escape(emp.getEmployeeNumber()),
                  escape(emp.getName()),
                  escape(emp.getEmail()),
                  escape(emp.getDepartment().getName()),
                  escape(emp.getPosition()),
                  escape(emp.getHireDate()),
                  escape(emp.getStatus())));
          writer.newLine();
        }
      }
    } catch (IOException e) {
      try {
        Files.deleteIfExists(path);
      } catch (IOException exception) {
      }
      throw new CustomRuntimeException(ExceptionType.BACKUP_FILE_EXEPTION);
    }

    return path;
  }

  private String escape(Object value) {
    String text = String.valueOf(value);
    return text.matches(".*[,\\\"\\r\\n].*") ? "\"" + text.replace("\"", "\"\"") + "\"" : text;
  }
}
