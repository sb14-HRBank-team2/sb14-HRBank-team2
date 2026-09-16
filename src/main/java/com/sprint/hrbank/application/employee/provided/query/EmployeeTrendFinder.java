package com.sprint.hrbank.application.employee.provided.query;

import com.sprint.hrbank.application.employee.dto.EmployeeTrendDto;
import java.util.List;

public interface EmployeeTrendFinder {

  List<EmployeeTrendDto> getTrend(String unit);
}
