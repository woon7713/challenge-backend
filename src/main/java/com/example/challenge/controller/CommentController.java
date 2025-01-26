package com.example.challenge.controller;

import com.example.challenge.dto.CommentRequestDto;
import com.example.challenge.dto.CommentResponseDto;
import com.example.challenge.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 1) 댓글 생성
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestParam Long postId,
            @RequestParam Long memberId,
            @RequestBody CommentRequestDto requestDto
    ) {
        CommentResponseDto createdComment = commentService.createComment(postId, memberId, requestDto);
        if (createdComment == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdComment);
    }

    // 2) 특정 게시글의 모든 댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentResponseDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    // 3) 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody String newContent
    ) {
        CommentResponseDto updatedComment = commentService.updateComment(commentId, newContent);
        if (updatedComment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedComment);
    }

    // 4) 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    // 5) 특정 게시글의 댓글 페이징
    @GetMapping("/post/{postId}/page")
    public ResponseEntity<Page<CommentResponseDto>> getPagedComments(
            @PathVariable Long postId,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<CommentResponseDto> comments = commentService.getPagedComments(postId, page, size);
        return ResponseEntity.ok(comments);
    }
}
