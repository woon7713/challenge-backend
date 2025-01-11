package com.example.challenge.repository;

import com.example.challenge.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // username으로 회원을 찾기 위한 메서드 (JWT 인증 시 사용 예정)
    Optional<Member> findByUsername(String username);

}
