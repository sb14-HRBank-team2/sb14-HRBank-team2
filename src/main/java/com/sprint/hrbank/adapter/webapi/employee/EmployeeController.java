package com.sprint.hrbank.adapter.webapi.employee;

import com.sprint.hrbank.application.employee.dto.CursorPageResponseEmployeeDto;
import com.sprint.hrbank.application.employee.dto.EmployeeCreateRequest;
import com.sprint.hrbank.application.employee.dto.EmployeeDto;
import com.sprint.hrbank.application.employee.dto.EmployeeUpdateRequest;
import com.sprint.hrbank.application.employee.provided.command.EmployeeCleaner;
import com.sprint.hrbank.application.employee.provided.command.EmployeeModifier;
import com.sprint.hrbank.application.employee.provided.command.EmployeeRegister;
import com.sprint.hrbank.application.employee.provided.query.EmployeeFinder;
import com.sprint.hrbank.application.employee.provided.query.EmployeePageMaker;
import com.sprint.hrbank.application.employee.provided.query.EmployeeSearchCond;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeRegister employeeRegister;
  private final EmployeeModifier employeeModifier;
  private final EmployeeCleaner employeeCleaner;
  private final EmployeeFinder employeeFinder;
  private final EmployeePageMaker pageMaker;

  // 직원등록
  @PostMapping
  public EmployeeDto createEmployee(
      @RequestPart EmployeeCreateRequest request,
      @RequestPart(value = "profile", required = false) MultipartFile profile,
      HttpServletRequest httpServletRequest) {
    String ipAddress = httpServletRequest.getRemoteAddr();
    return employeeRegister.register(request, ipAddress, profile);
  }

  // 직원삭제
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmployee(@PathVariable Long id, HttpServletRequest httpServletRequest) {
    String ipAddress = httpServletRequest.getRemoteAddr();
    employeeCleaner.deleteById(id, ipAddress);
  }

  // 직원수정
  @PatchMapping("/{id}")
  public EmployeeDto updateEmployee(
      @PathVariable Long id,
      @RequestPart EmployeeUpdateRequest request,
      HttpServletRequest httpServletRequest,
      @RequestPart(value = "profile", required = false) MultipartFile profile) {
    String ipAddress = httpServletRequest.getRemoteAddr();
    return employeeModifier.update(id, request, ipAddress, profile);
  }

  // 직원상세
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public EmployeeDto getEmployeeDetail(@PathVariable Long id) {
    return employeeFinder.getById(id);
  }

  // 직원목록
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public CursorPageResponseEmployeeDto getEmployeePage(@ModelAttribute EmployeeSearchCond cond) {
    return pageMaker.getEmployeePage(cond);
  }
}
