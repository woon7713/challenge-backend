package com.example.challenge.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;              // 댓글 ID

    @Column(nullable = false)
    private String content;       // 댓글 내용

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;            // 어떤 게시글에 달렸는지

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;        // 작성자
}
