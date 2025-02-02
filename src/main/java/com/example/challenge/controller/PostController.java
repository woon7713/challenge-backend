package com.example.challenge.controller;

import com.example.challenge.dto.PostRequestDto;
import com.example.challenge.dto.PostResponseDto;
import com.example.challenge.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 생성
    @PostMapping
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    // 전체 글 조회
    @GetMapping
    public List<PostResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

    // 단건 글 조회
    @GetMapping("/{postId}")
    public PostResponseDto getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    // 글 수정
    @PutMapping("/{postId}")
    public PostResponseDto updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequestDto requestDto
    ) {
        return postService.updatePost(postId, requestDto);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

    //게시글 페이징
    @GetMapping("/page")
    public Page<PostResponseDto> getPagePosts(
            @RequestParam int page,
            @RequestParam int size)
    {
        return postService.getPagePosts(page, size);
    }

    //게시물 검색
    @GetMapping("/search")
    public Page<PostResponseDto> searchPosts(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size
    ){
        return postService.searchPosts(keyword, page, size);

    }


}