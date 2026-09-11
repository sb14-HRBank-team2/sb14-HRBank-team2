package com.sprint.hrbank.service;

import com.sprint.hrbank.ChangeType;
import com.sprint.hrbank.domain.ChangeLog;
import com.sprint.hrbank.domain.Employee;
import com.sprint.hrbank.dto.EmployeeDto;
import com.sprint.hrbank.exception.CustomRuntimeException;
import com.sprint.hrbank.exception.ExceptionType;
import com.sprint.hrbank.repository.ChangeLogRepository;
import com.sprint.hrbank.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
  private final EmployeeRepository employeeRepository;
  private final ChangeLogRepository changeLogRepository;

  @Transactional(readOnly = true)
  public EmployeeDto getEmployeeDetail(Integer id) {
    Employee employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.USER_NOT_FOUND, id));
    return EmployeeDto.from(employee);
  }

  @Transactional
  public void deleteEmployee(Integer id, String ipAddress) {
    Employee employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.USER_NOT_FOUND, id));

    ChangeLog changeLog =
        ChangeLog.create(
            ChangeType.DELETED, employee.getEmployeeNumber(), "관리자에 의한 직원 영구 삭제", ipAddress);
    changeLogRepository.save(changeLog);

    // 프로필 사진 삭제하기 위한 코드 예시
    // if (employee.getProfileImageId() != null) {
    //     fileService.deleteFile(employee.getProfileImageId());
    // }

    employeeRepository.delete(employee);
  }
}
