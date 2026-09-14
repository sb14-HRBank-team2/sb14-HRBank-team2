package com.sprint.hrbank.advice;

import com.sprint.hrbank.exception.CustomRuntimeException;
import com.sprint.hrbank.exception.ErrorResponse;
import com.sprint.hrbank.exception.ExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleCustomRuntimeException(
            CustomRuntimeException exception) {
        ExceptionType exceptionType = exception.getType();
        // 로그찍기
        log.makeLoggingEventBuilder(exceptionType.getLevel())
                .setCause(exception)
                .log(exception.getMessage());
        // 프론트에 에러 쏘기
        return ResponseEntity.status(exceptionType.getStatus())
                .body(new ErrorResponse(exceptionType.getStatus(), exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOthersException(Exception exception) {
        // 백엔드 로그찍기
        log.error("정의되지 않은 예외 발생", exception);
        // 프론트에 에러 화면쏴주기
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 오류입니다"));
    }
}
