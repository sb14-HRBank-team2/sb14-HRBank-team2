package com.sprint.hrbank.application.employee;

import com.sprint.hrbank.adapter.persistence.employee.EmployeeSearchCond;
import com.sprint.hrbank.application.employee.dto.EmployeeDistributionDto;
import com.sprint.hrbank.application.employee.dto.EmployeeTrendDto;
import com.sprint.hrbank.application.employee.provided.query.EmployeeDistributionFinder;
import com.sprint.hrbank.application.employee.provided.query.EmployeeStatisticsFinder;
import com.sprint.hrbank.application.employee.provided.query.EmployeeTrendFinder;
import com.sprint.hrbank.application.employee.required.EmployeeRepository;
import com.sprint.hrbank.domain.employee.EmployeeStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class EmployeeStatisticsQueryService
    implements EmployeeStatisticsFinder, EmployeeDistributionFinder, EmployeeTrendFinder {

  private final EmployeeRepository employeeRepository;

  @Override
  public Long getCount(EmployeeStatus status, LocalDate fromDate) {
    EmployeeSearchCond cond =
        EmployeeSearchCond.builder().status(status).hireDateFrom(fromDate).build();
    return employeeRepository.countByCondition(cond);
  }

  @Override
  public List<EmployeeDistributionDto> getDistribution(String groupBy) {
    return employeeRepository.searchDistribution(groupBy);
  }

  @Override
  public List<EmployeeTrendDto> getTrend(String unit) {
    return employeeRepository.searchTrend(unit);
  }
}
