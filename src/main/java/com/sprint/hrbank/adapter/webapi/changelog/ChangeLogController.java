package com.sprint.hrbank.adapter.webapi.changelog;

import com.sprint.hrbank.adapter.persistence.changelog.ChangeLogSearchCond;
import com.sprint.hrbank.application.changelog.ChangeLogQueryService;
import com.sprint.hrbank.application.changelog.dto.CursorPageResponseChangeLogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/change-logs")
@RequiredArgsConstructor
public class ChangeLogController {

  private final ChangeLogQueryService changeLogQueryService;

  @GetMapping
  public CursorPageResponseChangeLogDto getChangeLogs(@ModelAttribute ChangeLogSearchCond cond) {
    return changeLogQueryService.getChangeLogPage(cond);
  }
}
