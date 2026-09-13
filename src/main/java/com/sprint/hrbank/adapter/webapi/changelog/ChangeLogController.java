package com.sprint.hrbank.adapter.webapi.changelog;

import com.sprint.hrbank.adapter.persistence.changelog.ChangeLogSearchCond;
import com.sprint.hrbank.application.changelog.ChangeLogService;
import com.sprint.hrbank.application.changelog.dto.ChangeLogResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/change-logs")
@RequiredArgsConstructor
public class ChangeLogController {

  private final ChangeLogService changeLogService;

  @GetMapping
  public List<ChangeLogResponseDto> getChangeLogs(@ModelAttribute ChangeLogSearchCond cond) {
    return changeLogService.getChangeLog(cond);
  }
}
