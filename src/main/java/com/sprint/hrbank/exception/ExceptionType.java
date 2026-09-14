package com.sprint.hrbank.exception;

import java.net.HttpURLConnection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.event.Level;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum ExceptionType {
    // 예시입니다 이런식으로 필요한 ENUM 추가하면 됩니다~
    USER_NOT_FOUND(Level.WARN, HttpURLConnection.HTTP_NOT_FOUND, "User with id %s not found");

    Level level;
    int status;
    String message;
}
