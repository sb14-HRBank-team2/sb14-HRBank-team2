package com.sprint.hrbank.repository;

import com.sprint.hrbank.entity.EmployeeHistory; // 팀원의 이력 엔티티 경로에 맞게 임포트 확인
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeHistoryRepository extends JpaRepository<EmployeeHistory, Integer> {
    // 특정 직원의 이력 조회 등 필요한 쿼리 메서드 정의
    List<EmployeeHistory> findByEmployeeId(Integer employeeId);
}