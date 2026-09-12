package com.sprint.hrbank.controller;

import com.sprint.hrbank.dto.EmployeeDto;
import com.sprint.hrbank.service.command.EmployeeCommandService;
import com.sprint.hrbank.service.query.EmployeeQueryService;
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

  private final EmployeeQueryService employeeService;
  private final EmployeeCommandService employeeCommandService;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public EmployeeDto getEmployeeDetail(@PathVariable Integer id) {
    return employeeService.getById(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmployee(@PathVariable Integer id, HttpServletRequest request) {
    String ipAddress = request.getRemoteAddr();
    employeeCommandService.deleteById(id);
  }
}
