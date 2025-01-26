package com.example.challenge.service;

import com.example.challenge.dto.CommentRequestDto;
import com.example.challenge.dto.CommentResponseDto;
import com.example.challenge.entity.Comment;
import com.example.challenge.entity.Member;
import com.example.challenge.entity.Post;
import com.example.challenge.repository.CommentRepository;
import com.example.challenge.repository.MemberRepository;
import com.example.challenge.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public CommentResponseDto createComment(Long postId, Long memberId, CommentRequestDto requestDto) {
        Optional<Post> postOpt = postRepository.findById(postId);
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        if (postOpt.isEmpty() || memberOpt.isEmpty()) {
            return null;
        }
        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .post(postOpt.get())
                .member(memberOpt.get())
                .build();
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getMember().getUsername(), // 작성자 아이디
                savedComment.getPost().getId()
        );
    }

    // 2) 특정 게시글의 모든 댓글 조회
    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .filter(c -> c.getPost().getId().equals(postId))
                .map(comment -> new CommentResponseDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getMember().getUsername(),
                        comment.getPost().getId()
                ))
                .collect(Collectors.toList());
    }

    // 3) 댓글 수정
    public CommentResponseDto updateComment(Long commentId, String newContent) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isEmpty()) {
            return null;
        }
        Comment existingComment = commentOpt.get();
        existingComment.setContent(newContent);
        Comment updatedComment = commentRepository.save(existingComment);
        return new CommentResponseDto(
                updatedComment.getId(),
                updatedComment.getContent(),
                updatedComment.getMember().getUsername(),
                updatedComment.getPost().getId()
        );
    }

    // 4) 댓글 삭제
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    // 5) 댓글 페이징
    public Page<CommentResponseDto> getPagedComments(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findByPostId(postId, pageable)
                .map(comment -> new CommentResponseDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getMember().getUsername(),
                        comment.getPost().getId()
                ));
    }
}
