package com.example.challenge.controller;

import com.example.challenge.dto.MemberRequestDto;
import com.example.challenge.dto.MemberResponseDto;
import com.example.challenge.security.JwtUtil;
import com.example.challenge.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

import java.util.Map;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    // 회원 가입
    @PostMapping("/register")
    public MemberResponseDto register(@RequestBody MemberRequestDto requestDto) {
        return memberService.register(requestDto);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberRequestDto requestDto) {
        MemberResponseDto memberResponseDto = memberService.login(requestDto);
        String token = jwtUtil.generateToken(memberResponseDto.getUsername());
        return ResponseEntity.ok(Map.of("token", token, "member", memberResponseDto));
    }
}
