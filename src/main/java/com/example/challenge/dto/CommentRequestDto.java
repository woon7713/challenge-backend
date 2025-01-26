package com.example.challenge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private Long postId;    // 댓글이 달릴 게시글 ID
    private Long memberId;  // 댓글 작성자 ID
    private String content; // 댓글 내용
}
