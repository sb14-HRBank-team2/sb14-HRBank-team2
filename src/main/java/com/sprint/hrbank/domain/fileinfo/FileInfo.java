package com.sprint.hrbank.domain.fileinfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Table(
    name = "file_info",
    uniqueConstraints = {@UniqueConstraint(name = "uk_file_info_name", columnNames = "name")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class FileInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "name", nullable = false)
  String name;

  @Column(nullable = false)
  String contentType;

  @Column(nullable = false)
  Long size;

  private FileInfo(String name, String contentType, Long size) {
    this.name = name;
    this.contentType = contentType;
    this.size = size;
  }

  public static FileInfo create(String name, String contentType, Long size) {
    return new FileInfo(name, contentType, size);
  }
}
