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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
                savedComment.getPost().getId(),
                savedComment.getCreatedAt(),
                savedComment.getUpdatedAt()
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
                        comment.getPost().getId(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    // 3) 댓글 수정
    public CommentResponseDto updateComment(Long commentId, String newContent) {
        // 댓글 조회 및 존재 확인
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        // 현재 인증된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member currentMember = (Member) authentication.getPrincipal();

        // 작성자 검증
        if (!comment.getMember().getId().equals(currentMember.getId())) {
            throw new IllegalArgumentException("작성자만 댓글을 수정할 수 있습니다.");
        }

        // 수정 진행
        comment.setContent(newContent);
        Comment updatedComment = commentRepository.save(comment);

        return new CommentResponseDto(
                updatedComment.getId(),
                updatedComment.getContent(),
                updatedComment.getMember().getUsername(),
                updatedComment.getPost().getId(),
                updatedComment.getCreatedAt(),
                updatedComment.getUpdatedAt()
        );
    }

    // 4) 댓글 삭제
    public void deleteComment(Long commentId) {
        // 삭제할 댓글 가져오기
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        // 현재 인증된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member currentMember = (Member) authentication.getPrincipal();

        // 댓글 작성자와 현재 인증 사용자가 일치하는지 확인
        if (!comment.getMember().getId().equals(currentMember.getId())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        // 삭제 진행
        commentRepository.delete(comment);
    }

    // 5) 댓글 페이징
    public Page<CommentResponseDto> getPagedComments(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findByPostId(postId, pageable)
                .map(comment -> new CommentResponseDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getMember().getUsername(),
                        comment.getPost().getId(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt()
                ));
    }
}
