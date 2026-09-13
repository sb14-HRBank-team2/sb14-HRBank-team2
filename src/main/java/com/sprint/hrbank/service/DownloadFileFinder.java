package com.sprint.hrbank.service;

import org.springframework.core.io.Resource;

import java.net.MalformedURLException;

public interface DownloadFileFinder {
    Resource downloadFile(Integer fileId) throws MalformedURLException;
}
