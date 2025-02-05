package com.example.challenge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;             // 게시글 ID

    @Column(nullable = false)
    private String title;        // 제목

    @Column(nullable = false)
    private String content;      // 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;       // 작성자(회원)와의 관계

    @Column(nullable = false)
    private int viewCount = 0; // 조회수

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; // 생성일

    @LastModifiedDate
    private LocalDateTime updatedAt; // 수정일

    // 조회수 증가 메서드
    public void increaseViewCount(){
        this.viewCount++;
    }

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();





}
