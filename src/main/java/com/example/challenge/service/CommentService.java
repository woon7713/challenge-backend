package com.example.challenge.service;


import com.example.challenge.entity.Comment;
import com.example.challenge.entity.Member;
import com.example.challenge.entity.Post;
import com.example.challenge.repository.CommentRepository;
import com.example.challenge.repository.MemberRepository;
import com.example.challenge.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public CommentService(
            CommentRepository commentRepository,
            PostRepository postRepository,
            MemberRepository memberRepository
    ) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    // 1) 댓글 생성
    public Comment createComment(Long postId, Long memberId, String content) {
        Optional<Post> postOpt = postRepository.findById(postId);
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        if (postOpt.isEmpty() || memberOpt.isEmpty()) {
            return null;
        }
        Comment comment = Comment.builder()
                .content(content)
                .post(postOpt.get())
                .member(memberOpt.get())
                .build();
        return commentRepository.save(comment);
    }


    // 2) 특정 게시글에 대한 모든 댓글 조회
    public List<Comment> getCommentsByPostId(Long postId) {
        List<Comment> allComments = commentRepository.findAll();
        return allComments.stream()
                .filter(c -> c.getPost().getId().equals(postId))
                .toList();
    }

    // 3) 댓글 수정
    public Comment updateComment(Long commentId, String newContent) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isEmpty()) {
            return null;
        }
        Comment existingComment = commentOpt.get();
        existingComment.setContent(newContent);
        return commentRepository.save(existingComment);
    }

    // 4) 댓글 삭제
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }




}
