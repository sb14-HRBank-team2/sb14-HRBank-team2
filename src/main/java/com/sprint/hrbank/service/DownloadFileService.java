package com.sprint.hrbank.service;

import com.sprint.hrbank.entity.File;
import com.sprint.hrbank.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
        // 1. 파일 메타데이터 가져옴
        File fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("해당 파일은 존재하지 않습니다."));

        // 2. 파일 주소(String) -> 서버 내부 파일 주소(URL)
        String path = fileEntity.getField();
        Path realPath = Paths.get(path);

        // + 파일이 존재하지 않을 수 있음
        if (!Files.exists(realPath)) {
            throw new RuntimeException("DB에 파일 경로는 있으나, 서버 하드디스크에 실제 파일이 존재하지 않습니다.");
        }

        // 3. 서버 내부 파일 주소(URL) -> 스프링이 알아들을 수 있는 파일 주소(file:/// + URL)
        URI springPath = realPath.toUri();

        // 4. 해당 파일 주소에 있는 파일을 가져오기
        Resource file = new UrlResource(springPath);

        // 5. 반환
        return file;
    }
}

