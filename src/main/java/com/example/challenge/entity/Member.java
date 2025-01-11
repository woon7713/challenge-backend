package com.example.challenge.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 기본 키

    @Column(nullable = false, unique = true)
    private String username;    // 회원 아이디

    @Column(nullable = false)
    private String password;    // 비밀번호

    @Column(nullable = false)
    private String email;       // 이메일

}
