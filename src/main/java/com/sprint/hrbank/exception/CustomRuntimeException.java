package com.sprint.hrbank.exception;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException {
    private final ExceptionType type;

    // 에러타입만 받을때
    public CustomRuntimeException(ExceptionType type) {
        super(type.getMessage());
        this.type = type;
    }

    // Object타입으로 받아서 Service코드에서 toString()으로 받을 필요가 없음
    // 에러타입이랑 무엇이 에러인지 같이 받을때
    public CustomRuntimeException(ExceptionType type, Object target) {
        super(String.format(type.getMessage(), target));
        this.type = type;
    }
}
