package com.sprint.hrbank.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.hrbank.application.changelog.ChangeLogQueryService;
import com.sprint.hrbank.application.changelog.required.ChangeLogRepository;
import com.sprint.hrbank.domain.chagelog.ChangeLog;
import com.sprint.hrbank.domain.chagelog.ChangeType;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(
    properties = {
      "spring.datasource.url=jdbc:h2:mem:changelog-count;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
      "spring.datasource.driver-class-name=org.h2.Driver",
      "spring.datasource.username=sa",
      "spring.datasource.password=",
      "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
      "spring.jpa.hibernate.ddl-auto=create-drop"
    })
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
class ChangeLogCountTest {

  private final ChangeLogQueryService changeLogQueryService;
  private final ChangeLogRepository changeLogRepository;

  @Test
  @DisplayName("지정한 기간의 변경 이력 건수를 조회한다")
  void getChangeLogCount() {
    changeLogRepository.save(ChangeLog.create(ChangeType.CREATED, "김어진", "등록", "127.0.0.1"));
    changeLogRepository.save(ChangeLog.create(ChangeType.UPDATED, "남어진", "수정", "127.0.0.2"));

    LocalDateTime now = LocalDateTime.now();

    assertThat(changeLogQueryService.getChangeLogCount(now.minusMinutes(1), now.plusMinutes(1)))
        .isEqualTo(2);
    assertThat(changeLogQueryService.getChangeLogCount(now.minusDays(2), now.minusDays(1)))
        .isZero();
  }
}
