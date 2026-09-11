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
  /*가장 많이 쓸거같은 예외 3개를 추가했습니다
  필요한 예외의 형식은 아래의 예외타입대로 작성해주시면 됩니다
   */
  USER_NOT_FOUND(Level.WARN, HttpURLConnection.HTTP_NOT_FOUND, "User with id %s not found"),
  DEPARTMENT_NOT_FOUND(
      Level.WARN, HttpURLConnection.HTTP_NOT_FOUND, "Department with id %s not found"),
  INVALID_REQUEST(Level.WARN, HttpURLConnection.HTTP_BAD_REQUEST, "Invalid request"),
  CHANGE_LOG_NOT_FOUND(
      Level.WARN, HttpURLConnection.HTTP_NOT_FOUND, "ChangeLog with id %s not found");

  Level level;
  int status;
  String message;
}
