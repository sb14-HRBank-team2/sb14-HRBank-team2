package com.sprint.hrbank.adapter.webapi.employee;

import com.sprint.hrbank.application.employee.EmployeeService;
import com.sprint.hrbank.application.employee.dto.EmployeeDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
  private final EmployeeService employeeService;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public EmployeeDto getEmployeeDetail(@PathVariable Integer id) {
    return employeeService.getEmployeeDetail(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmployee(@PathVariable Integer id, HttpServletRequest request) {
    String ipAddress = request.getRemoteAddr();
    employeeService.deleteEmployee(id, ipAddress);
  }
}
