package com.sprint.hrbank.application.employee.provided.query;

import com.sprint.hrbank.application.employee.dto.EmployeeDistributionDto;
import java.util.List;

public interface EmployeeDistributionFinder {

  List<EmployeeDistributionDto> getDistribution(String groupBy);
}
