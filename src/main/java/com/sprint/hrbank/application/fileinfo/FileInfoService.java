package com.sprint.hrbank.application.fileinfo;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.deleteIfExists;
import static java.nio.file.Files.size;
import static java.util.UUID.randomUUID;

import com.sprint.hrbank.application.fileinfo.dto.FileDownloadDto;
import com.sprint.hrbank.application.fileinfo.required.FileInfoRepository;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.fileinfo.FileInfo;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileInfoService {

  private final FileInfoRepository fileInfoRepository;

  @Value("${file.dir:/tmp/hrbank/files/}")
  private String fileDirectory;

  @Transactional(readOnly = true)
  public FileDownloadDto downloadFile(Long fileId) {
    FileInfo fileInfo =
        fileInfoRepository
            .findById(fileId)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.INVALID_REQUEST));

    try {
      Path filePath = Paths.get(fileDirectory + fileInfo.getName());

      Resource resource = new UrlResource(filePath.toUri());

      if (!resource.exists() || !resource.isReadable()) {
        throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
      }

      return FileDownloadDto.builder()
          .fileName(fileInfo.getName())
          .contentType(fileInfo.getContentType())
          .resource(resource)
          .build();

    } catch (MalformedURLException e) {
      throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
    }
  }

  @Transactional
  public Long uploadFile(MultipartFile file) {
    try {
      String originalFilename = file.getOriginalFilename();
      String extension = "";
      if (originalFilename != null && originalFilename.contains(("."))) {
        extension = originalFilename.substring(originalFilename.lastIndexOf("."));
      }

      String saveFileName = randomUUID().toString() + extension;

      Path targetPath = Paths.get(fileDirectory + saveFileName);
      if (targetPath.getParent() != null) {
        createDirectories(targetPath.getParent());
      }

      file.transferTo(targetPath.toFile());

      FileInfo fileInfo = FileInfo.create(saveFileName, file.getContentType(), file.getSize());

      FileInfo savedInfo = fileInfoRepository.save(fileInfo);

      return savedInfo.getId();

    } catch (IOException e) {
      throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
    }
  }

  @Transactional
  public void deleteFile(Long fileId) {
    if (fileId == null) {
      return;
    }

    FileInfo fileInfo =
        fileInfoRepository
            .findById(fileId)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.INVALID_REQUEST));

    Path filePath = Paths.get(fileDirectory + fileInfo.getName());
    try {
      deleteIfExists(filePath);
    } catch (IOException e) {
      throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
    }

    fileInfoRepository.delete(fileInfo);
  }

  @Transactional
  public FileInfo register(Path path) {
    try {
      return fileInfoRepository.save(
          FileInfo.create(path.getFileName().toString(), "text/csv", size(path)));
    } catch (IOException e) {
      throw new CustomRuntimeException(ExceptionType.BACKUP_FILE_EXEPTION);
    }
  }
}
