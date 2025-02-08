package com.example.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;         // 댓글 ID
    private String content;  // 댓글 내용
    private String username; // 작성자 이름
    private Long postId;     // 댓글이 달린 게시글 ID
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
