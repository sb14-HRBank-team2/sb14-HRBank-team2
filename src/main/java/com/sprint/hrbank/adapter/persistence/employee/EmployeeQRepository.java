package com.sprint.hrbank.adapter.persistence.employee;

import com.sprint.hrbank.application.employee.dto.EmployeeDistributionDto;
import com.sprint.hrbank.application.employee.dto.EmployeeTrendDto;
import com.sprint.hrbank.domain.employee.Employee;
import java.util.List;

public interface EmployeeQRepository {

  List<Employee> search(EmployeeSearchCond employeeSearchCond);

  Long countByCondition(EmployeeSearchCond cond);

  List<EmployeeDistributionDto> searchDistribution(String groupBy);

  List<EmployeeTrendDto> searchTrend(String unit);
}
