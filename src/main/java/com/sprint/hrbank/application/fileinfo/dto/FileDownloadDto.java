package com.sprint.hrbank.application.fileinfo.dto;

import lombok.Builder;
import org.springframework.core.io.Resource;

@Builder
public record FileDownloadDto(String fileName, String contentType, Resource resource) {}
