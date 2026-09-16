package com.sprint.hrbank.adapter.webapi.employee;

import com.sprint.hrbank.application.employee.dto.EmployeeDistributionDto;
import com.sprint.hrbank.application.employee.dto.EmployeeTrendDto;
import com.sprint.hrbank.application.employee.provided.query.EmployeeDistributionFinder;
import com.sprint.hrbank.application.employee.provided.query.EmployeeStatisticsFinder;
import com.sprint.hrbank.application.employee.provided.query.EmployeeTrendFinder;
import com.sprint.hrbank.domain.employee.EmployeeStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeStatisticsController {

  private final EmployeeStatisticsFinder statisticsFinder;
  private final EmployeeTrendFinder trendFinder;
  private final EmployeeDistributionFinder distributionFinder;

  @GetMapping("/count")
  @ResponseStatus(HttpStatus.OK)
  public Long getEmployeeCount(
      @RequestParam(required = false) EmployeeStatus status,
      @RequestParam(required = false) LocalDate fromDate) {
    return statisticsFinder.getCount(status, fromDate);
  }

  @GetMapping("/stats/trend")
  public List<EmployeeTrendDto> getEmployeeTrend(
      @RequestParam(required = false) LocalDate from,
      @RequestParam(required = false) LocalDate to,
      @RequestParam(required = false, defaultValue = "month") String unit) {
    return trendFinder.getTrend(unit);
  }

  @GetMapping("/stats/distribution")
  public List<EmployeeDistributionDto> getEmployeeDistribution(
      @RequestParam String groupBy,
      @RequestParam(defaultValue = "ACTIVE", required = false) EmployeeStatus status) {
    return distributionFinder.getDistribution(groupBy);
  }
}
