package com.example.challenge.service;

import com.example.challenge.dto.MemberRequestDto;
import com.example.challenge.dto.MemberResponseDto;
import com.example.challenge.entity.Member;
import com.example.challenge.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    public MemberResponseDto register(MemberRequestDto requestDto) {
        // 1) 중복 체크
        if (memberRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 username 입니다.");
        }

        // 2) 엔티티 생성
        Member member = Member.builder()
                .username(requestDto.getUsername())
                .password(requestDto.getPassword()) // 비밀번호 암호화 필요
                .email(requestDto.getEmail())
                .build();

        // 3) DB 저장
        Member savedMember = memberRepository.save(member);

        // 4) 응답 DTO 변환
        return new MemberResponseDto(savedMember.getId(), savedMember.getUsername(), savedMember.getEmail());
    }

    // 로그인 (간단 버전, JWT 적용 전)
    public MemberResponseDto login(MemberRequestDto requestDto) {
        Member member = memberRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 username 입니다."));

        // (비밀번호 확인 로직)
        if (!member.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 로그인 성공 → MemberResponseDto 반환
        return new MemberResponseDto(member.getId(), member.getUsername(), member.getEmail());
    }
}
