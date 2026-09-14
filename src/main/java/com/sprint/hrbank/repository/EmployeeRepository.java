package com.sprint.hrbank.repository;

import com.sprint.hrbank.dto.DepartmentDistributionDto;
import com.sprint.hrbank.dto.MonthlyTrendDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(
            "SELECT new com.sprint.hrbank.dto.DepartmentDistributionDto(d.name, COUNT(e.id)) "
                    + "FROM Employee e JOIN e.department d GROUP BY d.name")
    List<DepartmentDistributionDto> findDepartmentDistribution();

    @Query(
            "SELECT new com.sprint.hrbank.dto.MonthlyTrendDto(SUBSTRING(CAST(e.hireDate AS string), 1, 7), COUNT(e.id)) "
                    + "FROM Employee e WHERE e.hireDate IS NOT NULL GROUP BY SUBSTRING(CAST(e.hireDate AS string), 1, 7)")
    List<MonthlyTrendDto> findMonthlyTrend();

    List<Employee> findTop5ByOrderByIdDesc();
}
