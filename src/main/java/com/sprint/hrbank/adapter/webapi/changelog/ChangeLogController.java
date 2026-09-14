package com.sprint.hrbank.adapter.webapi.changelog;

import com.sprint.hrbank.application.changelog.ChangeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChangeLogController {

  private final ChangeLogService changeLogService;

  //    @GetMapping("/api/change-logs")

}
