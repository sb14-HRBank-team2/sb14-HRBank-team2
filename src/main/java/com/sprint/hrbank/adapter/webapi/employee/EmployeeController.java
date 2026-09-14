package com.sprint.hrbank.adapter.webapi.employee;

import com.sprint.hrbank.application.employee.dto.CursorPageResponseEmployeeDto;
import com.sprint.hrbank.application.employee.dto.EmployeeDto;
import com.sprint.hrbank.application.employee.provided.command.EmployeeCleaner;
import com.sprint.hrbank.application.employee.provided.query.EmployeeFinder;
import com.sprint.hrbank.application.employee.provided.query.EmployeePageMaker;
import com.sprint.hrbank.application.employee.provided.query.EmployeeSearchCond;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeFinder employeeFinder;
  private final EmployeePageMaker pageMaker;
  private final EmployeeCleaner employeeCleaner;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public EmployeeDto getEmployeeDetail(@PathVariable Long id) {
    return employeeFinder.getById(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public CursorPageResponseEmployeeDto getEmployeePage(@ModelAttribute EmployeeSearchCond cond) {
    return pageMaker.getEmployeePage(cond);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmployee(@PathVariable Long id, HttpServletRequest request) {
    String ipAddress = request.getRemoteAddr();
    employeeCleaner.deleteById(id, ipAddress);
  }
}
