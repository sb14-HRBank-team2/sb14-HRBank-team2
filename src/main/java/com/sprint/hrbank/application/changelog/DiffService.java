package com.sprint.hrbank.application.changelog;

import com.sprint.hrbank.application.changelog.dto.DiffCreateRequestDto;
import com.sprint.hrbank.application.changelog.dto.DiffResponseDto;
import com.sprint.hrbank.application.changelog.required.ChangeLogRepository;
import com.sprint.hrbank.application.changelog.required.DiffRepository;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.chagelog.ChangeLog;
import com.sprint.hrbank.domain.chagelog.Diff;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiffService {

  private final DiffRepository diffRepository;
  private final ChangeLogRepository changeLogRepository;

  public DiffResponseDto create(DiffCreateRequestDto dto, Integer changeLogId) {
    // 아디로 이력 객체로 가져와서 diff에 박아줘야함
    ChangeLog changeLog =
        changeLogRepository
            .findById(changeLogId)
            .orElseThrow(
                () -> new CustomRuntimeException(ExceptionType.CHANGE_LOG_NOT_FOUND, changeLogId));

    //    Diff target = dto.toEntity(changeLog);
    Diff diff = Diff.create(dto.propertyName(), dto.before(), dto.after(), changeLog);
    return DiffResponseDto.from(diffRepository.save(diff));
  }

  public List<DiffResponseDto> readAll(Integer changeLogId) {
    List<Diff> retrievedList = diffRepository.findAllByChangeLogId(changeLogId);

    List<DiffResponseDto> result = new ArrayList<>();
    for (Diff each : retrievedList) {
      DiffResponseDto dto = DiffResponseDto.from(each);
      result.add(dto);
    }
    return result;
  }
}
