package com.sprint.hrbank.controller;

import com.sprint.hrbank.service.ChangeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChangeLogController {

  private final ChangeLogService changeLogService;

  //    @GetMapping("/api/change-logs")

}
