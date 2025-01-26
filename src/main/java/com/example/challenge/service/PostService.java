package com.example.challenge.service;

import com.example.challenge.dto.PostRequestDto;
import com.example.challenge.dto.PostResponseDto;
import com.example.challenge.entity.Member;
import com.example.challenge.entity.Post;
import com.example.challenge.repository.MemberRepository;
import com.example.challenge.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // (1) 글 생성
    public PostResponseDto createPost(PostRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 memberId가 존재하지 않습니다."));

        // 2) 엔티티 생성
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(member)
                .build();

        // 3) DB 저장
        Post savedPost = postRepository.save(post);

        // 4) 응답 DTO 변환
        return new PostResponseDto(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getMember().getUsername()  // 작성자 username
        );
    }

    // (2) 전체 게시글 조회
    public List<PostResponseDto> getAllPosts() {
        List<Post> postList = postRepository.findAll();

        // 엔티티 리스트를 DTO 리스트로 변환
        return postList.stream()
                .map(post -> new PostResponseDto(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getMember().getUsername()
                ))
                .collect(Collectors.toList());
    }

    // (3) 게시글 조회
    public PostResponseDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember().getUsername()
        );
    }

    // (4) 게시글 수정
    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));


        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        Post updatedPost = postRepository.save(post);

        return new PostResponseDto(
                updatedPost.getId(),
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getMember().getUsername()
        );
    }

    // (5) 게시글 삭제
    public void deletePost(Long postId) {
        // (작성자 본인이 맞는지 검증 추가 필요)
        postRepository.deleteById(postId);
    }

    // (6) 게시글 페이지
    public Page<PostResponseDto> getPagePosts(int page, int size){
        Pageable pageable = PageRequest.of(page, size); // 페이지, 크기 설정
        Page<Post> postPage = postRepository.findAll(pageable);

        // Page<Post> -> Page<PostResponseDto> 변환
        return postPage.map(post -> new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember().getUsername()
        ));

    }


}