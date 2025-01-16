package com.example.challenge.controller;

import com.example.challenge.entity.Comment;
import com.example.challenge.service.CommentService;
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
    public ResponseEntity<Comment> createComment(
            @RequestParam Long postId,
            @RequestParam Long memberId,
            @RequestBody String content
    ) {
        Comment createdComment = commentService.createComment(postId, memberId, content);
        if (createdComment == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdComment);
    }

    // 2) 특정 게시글의 모든 댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    // 3) 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long commentId,
            @RequestBody String newContent
    ) {
        Comment updatedComment = commentService.updateComment(commentId, newContent);
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
}
