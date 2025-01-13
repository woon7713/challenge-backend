package com.example.challenge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
    private String title;   // 제목
    private String content; // 내용

    private Long memberId;  // 작성자 memberId
}
