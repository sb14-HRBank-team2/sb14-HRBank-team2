package com.sprint.hrbank.controller;

import com.sprint.hrbank.dto.EmployeeDto;
import com.sprint.hrbank.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
}
