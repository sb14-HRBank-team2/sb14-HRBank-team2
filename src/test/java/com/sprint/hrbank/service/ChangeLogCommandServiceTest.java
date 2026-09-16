package com.sprint.hrbank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.sprint.hrbank.application.changelog.ChangeLogCommandService;
import com.sprint.hrbank.application.changelog.dto.ChangeLogCreateRequestDto;
import com.sprint.hrbank.application.changelog.dto.ChangeLogDto;
import com.sprint.hrbank.application.changelog.dto.DiffCreateRequestDto;
import com.sprint.hrbank.application.changelog.required.ChangeLogRepository;
import com.sprint.hrbank.application.changelog.required.DiffRepository;
import com.sprint.hrbank.domain.chagelog.ChangeLog;
import com.sprint.hrbank.domain.chagelog.ChangeType;
import com.sprint.hrbank.domain.chagelog.Diff;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class ChangeLogCommandServiceTest {

  @Autowired private ChangeLogCommandService changeLogCommandService;

  @Autowired private HttpServletRequest httpServletRequest;

  @Autowired private ChangeLogRepository changeLogRepository;

  @Autowired private DiffRepository diffRepository;

  @Test
  @DisplayName("로그와 변경내용 저장되는지 확인")
  void saveChangeLog() {
    // given
    ChangeLogCreateRequestDto request =
        new ChangeLogCreateRequestDto(
            ChangeType.CREATED,
            "testString",
            "직원등록 잘되나 확인하는 절차입니다.",
            List.of(
                new DiffCreateRequestDto("이름", null, "김어진"),
                new DiffCreateRequestDto("직함", null, "사원")));

    // when
    ChangeLogDto response =
        changeLogCommandService.create(request, httpServletRequest.getRemoteAddr());

    ChangeLog saved = changeLogRepository.findById(response.id()).orElseThrow();

    // then
    assertThat(saved.getEmployeeNumber()).isEqualTo("testString");
    assertThat(saved.getIpAddress()).isEqualTo(httpServletRequest.getRemoteAddr());
    List<Diff> savedDiffs = diffRepository.findAllByChangeLogId(response.id());
    assertThat(savedDiffs)
        .extracting(Diff::getPropertyName, Diff::getBefore, Diff::getAfter)
        .containsExactlyInAnyOrder(tuple("이름", null, "김어진"), tuple("직함", null, "사원"));
    assertThat(savedDiffs)
        .allSatisfy(diff -> assertThat(diff.getChangeLog().getId()).isEqualTo(response.id()));
  }
}
