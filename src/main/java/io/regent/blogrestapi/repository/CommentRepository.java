package io.regent.blogrestapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.regent.blogrestapi.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentByPostId(final Long postId);
}
