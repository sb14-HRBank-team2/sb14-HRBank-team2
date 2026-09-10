package com.sprint.hrbank.repository;

import com.sprint.hrbank.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // 1. 상태별(예: "재직중", "ACTIVE" 등) 총 직원 수 구하기
    long countByStatus(String status);

    // ==========================================
    // 2. 부서별 직원 수 분포 쿼리
    // ==========================================
    @Query("SELECT d.name AS departmentName, COUNT(e.id) AS employeeCount " +
            "FROM Employee e JOIN e.department d " +
            "WHERE e.status = '재직중' " + // 필요시 상태 조건 수정 (ACTIVE 등)
            "GROUP BY d.name")
    List<DeptStats> countEmployeesByDepartment();

    // 결과를 담을 내부 인터페이스 (Projection)
    interface DeptStats {
        String getDepartmentName();
        Long getEmployeeCount();
    }

    // ==========================================
    // 3. 월별 입사자 추이 쿼리
    // ==========================================
    // joinDate가 "2026-09-10" 형태의 문자열이라고 가정하고 앞 7자리(YYYY-MM)만 잘라서 통계
    @Query("SELECT SUBSTRING(e.joinDate, 1, 7) AS yearMonth, COUNT(e.id) AS joinCount " +
            "FROM Employee e " +
            "GROUP BY SUBSTRING(e.joinDate, 1, 7) " +
            "ORDER BY yearMonth DESC")
    List<MonthlyStats> countJoinTrend();

    interface MonthlyStats {
        String getYearMonth();
        Long getJoinCount();
    }
}