package com.sprint.hrbank.repository;

import com.sprint.hrbank.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
}

