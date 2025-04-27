package com.example.challenge.exception;

/**
 * 비즈니스 로직에서 상태 코드(code)와 메시지(message)를 함께 던지기 위한 예외.
 */
public class MyBusinessException extends RuntimeException {
    // 클라이언트·로그에서 식별할 수 있는 코드
    private final String code;

    // code와 message를 모두 받아서 예외를 생성
    public MyBusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    // 예외 처리기에서 꺼내 쓸 수 있도록 getter 추가
    public String getCode() {
        return code;
    }
}
