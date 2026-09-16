package com.sprint.hrbank.application.employee.provided.command;

import com.sprint.hrbank.application.employee.dto.EmployeeDto;
import com.sprint.hrbank.application.employee.dto.EmployeeUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeModifier {

  EmployeeDto update(
      Long employeeId, EmployeeUpdateRequest request, String ipAddress, MultipartFile profile);
}
