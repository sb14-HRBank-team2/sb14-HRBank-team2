package com.sprint.hrbank.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum EmployeeStatus {
    WORKING("재직중"),
    NOT("퇴사");

    String description;
}
