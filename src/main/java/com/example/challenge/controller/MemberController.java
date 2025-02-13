package com.example.challenge.controller;

import com.example.challenge.dto.MemberRequestDto;
import com.example.challenge.dto.MemberResponseDto;
import com.example.challenge.security.JwtProvider;
import com.example.challenge.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    // 회원 가입
    @PostMapping("/register")
    public MemberResponseDto register(@RequestBody MemberRequestDto requestDto) {
        return memberService.register(requestDto);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberRequestDto requestDto) {
        MemberResponseDto memberResponseDto = memberService.login(requestDto);
        String token = jwtProvider.generateToken(memberResponseDto.getUsername());
        return ResponseEntity.ok(Map.of("token", token, "member", memberResponseDto));
    }
}
