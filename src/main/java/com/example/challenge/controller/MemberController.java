package com.example.challenge.controller;

import com.example.challenge.dto.MemberRequestDto;
import com.example.challenge.dto.MemberResponseDto;
import com.example.challenge.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 가입
    @PostMapping("/register")
    public MemberResponseDto register(@RequestBody MemberRequestDto requestDto) {
        return memberService.register(requestDto);
    }

    // 로그인
    @PostMapping("/login")
    public MemberResponseDto login(@RequestBody MemberRequestDto requestDto) {
        return memberService.login(requestDto);
    }
}
