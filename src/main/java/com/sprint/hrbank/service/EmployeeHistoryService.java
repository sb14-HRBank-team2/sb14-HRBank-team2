package com.sprint.hrbank.service;

import com.sprint.hrbank.dto.EmployeeHistoryDto;
import com.sprint.hrbank.repository.EmployeeHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeHistoryService {

    private final EmployeeHistoryRepository employeeHistoryRepository;

    public List<EmployeeHistoryDto> getEmployeeHistories(Integer employeeId) {
        return employeeHistoryRepository.findByEmployeeId(employeeId).stream()
                .map(history -> new EmployeeHistoryDto(
                        history.getId(),
                        history.getEmployeeId(),
                        history.getChangeContent(),
                        history.getModifiedAt()
                ))
                .collect(Collectors.toList());
    }
}