package com.example.challenge.advice;

import com.example.challenge.dto.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 모든 컨트롤러 응답을 ApiResponse 포맷으로 자동 감싸 줍니다.
 */
@ControllerAdvice
public class ApiResponseWrapper implements ResponseBodyAdvice<Object> {

    // 1) 이 Advice를 어떤 반환 타입에 적용할지 결정
    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        // ApiResponse 자체나 ResponseEntity로 이미 감싸진 타입은 스킵
        Class<?> clazz = returnType.getParameterType();
        return !ApiResponse.class.isAssignableFrom(clazz)
                && !ResponseEntity.class.isAssignableFrom(clazz);
    }

    // 2) 실제로 바디를 쓰기 직전에 호출되어 감싸는 로직 수행
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        // String은 MessageConverter 이슈가 있으니 별도 처리하거나 스킵
        if (body instanceof String) {
            return body;
        }
        // 정상 리턴값은 모두 ApiResponse.ok(body) 로 감싸서 반환
        return ApiResponse.ok(body);
    }
}
