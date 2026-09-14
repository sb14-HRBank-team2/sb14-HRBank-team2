package com.sprint.hrbank.application.fileinfo.required;

import com.sprint.hrbank.domain.fileinfo.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {}
