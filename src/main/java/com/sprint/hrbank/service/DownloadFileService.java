package com.sprint.hrbank.service;

import com.sprint.hrbank.entity.File;
import com.sprint.hrbank.exception.CustomRuntimeException;
import com.sprint.hrbank.exception.ExceptionType;
import com.sprint.hrbank.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class DownloadFileService implements DownloadFileFinder {
    private final FileRepository fileRepository;

    // 다운로드 파일
    public Resource downloadFile(Integer fileId) throws MalformedURLException {
        // String -> Path -> URI -> Resource(URI) -> Resource(URI) + Header

        // 1. 파일 메타데이터 가져옴
         File fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new CustomRuntimeException(ExceptionType.FILE_NOT_FOUND));

        // 2. 파일 주소(String) -> 서버 내부 파일 주소(URL)
         String path = fileEntity.getField();
         Path realPath = Paths.get(path);
        // Path realPath = Paths.get("/Users/codeit/Desktop/test.csv"); // (테스트용)

        // + 파일이 존재하지 않을 수 있음
        if (!Files.exists(realPath)) {
            throw new CustomRuntimeException(ExceptionType.FILE_NOT_FOUND);
        }

        URI springPath = realPath.toUri();

        // 5. 반환
        return new UrlResource(springPath);
    }
}

