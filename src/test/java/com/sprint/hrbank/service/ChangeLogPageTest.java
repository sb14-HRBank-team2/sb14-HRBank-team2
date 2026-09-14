package com.sprint.hrbank.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.hrbank.adapter.persistence.changelog.ChangeLogSearchCond;
import com.sprint.hrbank.application.changelog.dto.CursorPageResponseChangeLogDto;
import com.sprint.hrbank.application.changelog.provided.query.ChangeLogPageMaker;
import com.sprint.hrbank.application.changelog.required.ChangeLogRepository;
import com.sprint.hrbank.domain.chagelog.ChangeLog;
import com.sprint.hrbank.domain.chagelog.ChangeType;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(
    properties = {
      "spring.datasource.url=jdbc:h2:mem:changelog-page;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
      "spring.datasource.driver-class-name=org.h2.Driver",
      "spring.datasource.username=sa",
      "spring.datasource.password=",
      "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
      "spring.jpa.hibernate.ddl-auto=create-drop"
    })
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
class ChangeLogPageTest {

  private final ChangeLogPageMaker changeLogPageMaker;
  private final ChangeLogRepository changeLogRepository;

  @Test
  @DisplayName("변경 이력을 커서로 다음 페이지까지 조회한다")
  void getChangeLogPage() {
    changeLogRepository.save(ChangeLog.create(ChangeType.CREATED, "김어진", "등록", "127.0.0.1"));
    changeLogRepository.save(ChangeLog.create(ChangeType.UPDATED, "최어진", "수정", "127.0.0.2"));
    changeLogRepository.save(ChangeLog.create(ChangeType.DELETED, "이어진", "삭제", "127.0.0.3"));

    CursorPageResponseChangeLogDto firstPage =
        changeLogPageMaker.getChangeLogPage(ChangeLogSearchCond.builder().size(2).build());
    CursorPageResponseChangeLogDto secondPage =
        changeLogPageMaker.getChangeLogPage(
            ChangeLogSearchCond.builder()
                .size(2)
                .cursor(firstPage.nextCursor())
                .idAfter(firstPage.nextIdAfter())
                .build());

    assertThat(firstPage.content()).hasSize(2);
    assertThat(firstPage.totalElements()).isEqualTo(3);
    assertThat(firstPage.hasNext()).isTrue();
    assertThat(firstPage.nextCursor()).isNotNull();
    assertThat(firstPage.nextIdAfter()).isNotNull();

    assertThat(secondPage.content()).hasSize(1);
    assertThat(secondPage.totalElements()).isEqualTo(3);
    assertThat(secondPage.hasNext()).isFalse();
    assertThat(secondPage.nextCursor()).isNull();
    assertThat(secondPage.nextIdAfter()).isNull();
    assertThat(secondPage.content().get(0).id())
        .isNotIn(firstPage.content().stream().map(changeLog -> changeLog.id()).toList());
  }

  @Test
  @DisplayName("IP 주소 오름차순에서도 커서로 다음 페이지를 조회한다")
  void getChangeLogPageSortedByIpAddress() {
    changeLogRepository.save(ChangeLog.create(ChangeType.CREATED, "EMP-001", "등록", "127.0.0.3"));
    changeLogRepository.save(ChangeLog.create(ChangeType.UPDATED, "EMP-002", "수정", "127.0.0.1"));
    changeLogRepository.save(ChangeLog.create(ChangeType.DELETED, "EMP-003", "삭제", "127.0.0.2"));

    CursorPageResponseChangeLogDto firstPage =
        changeLogPageMaker.getChangeLogPage(
            ChangeLogSearchCond.builder()
                .size(2)
                .sortField("ipAddress")
                .sortDirection("asc")
                .build());
    CursorPageResponseChangeLogDto secondPage =
        changeLogPageMaker.getChangeLogPage(
            ChangeLogSearchCond.builder()
                .size(2)
                .sortField("ipAddress")
                .sortDirection("asc")
                .cursor(firstPage.nextCursor())
                .idAfter(firstPage.nextIdAfter())
                .build());

    assertThat(firstPage.content().stream().map(changeLog -> changeLog.ipAddress()).toList())
        .containsExactly("127.0.0.1", "127.0.0.2");
    assertThat(firstPage.nextCursor()).isEqualTo("127.0.0.2");
    assertThat(secondPage.content().stream().map(changeLog -> changeLog.ipAddress()).toList())
        .containsExactly("127.0.0.3");
    assertThat(secondPage.hasNext()).isFalse();
  }
}
