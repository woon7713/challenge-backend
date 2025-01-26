package com.example.challenge.repository;

import com.example.challenge.entity.Comment;
import com.example.challenge.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostId(Long PostId, Pageable pageable);
}
