package com.sprint.hrbank.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.hrbank.adapter.persistence.changelog.ChangeLogSearchCond;
import com.sprint.hrbank.application.changelog.required.ChangeLogRepository;
import com.sprint.hrbank.domain.chagelog.ChangeLog;
import com.sprint.hrbank.domain.chagelog.ChangeType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(
    properties = {
      "spring.datasource.url=jdbc:h2:mem:changelog-search;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
      "spring.datasource.driver-class-name=org.h2.Driver",
      "spring.datasource.username=sa",
      "spring.datasource.password=",
      "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
      "spring.jpa.hibernate.ddl-auto=create-drop"
    })
@Slf4j
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
class ChangeLogSearchTest {

  private final ChangeLogRepository changeLogRepository;

  @Test
  @Transactional
  @DisplayName("사번과 유형 조건을 모두 만족하는 변경 이력을 조회한다")
  void searchByCondition() {
    // given
    ChangeLog changeLog = ChangeLog.create(ChangeType.UPDATED, "김어진", "직함 변경", "127.0.0.1");
    changeLogRepository.save(changeLog);
    changeLogRepository.save(ChangeLog.create(ChangeType.CREATED, "이어진", "직원 등록", "127.0.0.1"));
    changeLogRepository.save(ChangeLog.create(ChangeType.UPDATED, "최어진", "직함 변경", "127.0.0.1"));

    ChangeLogSearchCond cond =
        ChangeLogSearchCond.builder().employeeNumber("김").type(ChangeType.UPDATED).build();

    // when
    List<ChangeLog> result = changeLogRepository.search(cond);

    // then
    log.info("cond={}", cond);
    log.info("result={}", result);
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getId()).isEqualTo(changeLog.getId());
  }
}
