package com.sprint.hrbank.repository;

import com.sprint.hrbank.dto.DepartmentDistributionDto;
import com.sprint.hrbank.dto.MonthlyTrendDto;
import com.sprint.hrbank.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT new com.sprint.hrbank.dto.DepartmentDistributionDto(d.name, COUNT(e.id)) " +
            "FROM Employee e JOIN e.department d GROUP BY d.name")
    List<DepartmentDistributionDto> findDepartmentDistribution();

    @Query("SELECT new com.sprint.hrbank.dto.MonthlyTrendDto(SUBSTRING(CAST(e.hireDate AS string), 1, 7), COUNT(e.id)) " +
            "FROM Employee e GROUP BY SUBSTRING(CAST(e.hireDate AS string), 1, 7)")
    List<MonthlyTrendDto> findMonthlyTrend();
}