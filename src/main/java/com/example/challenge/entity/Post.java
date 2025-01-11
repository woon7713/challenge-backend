package com.example.challenge.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;             // 게시글 ID

    @Column(nullable = false)
    private String title;        // 제목

    @Column(nullable = false)
    private String content;      // 내용

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;       // 작성자(회원)와의 관계

}
