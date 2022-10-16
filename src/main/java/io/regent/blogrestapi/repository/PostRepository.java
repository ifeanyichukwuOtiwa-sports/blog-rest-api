package io.regent.blogrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.regent.blogrestapi.entity.BlogPost;

public interface PostRepository extends JpaRepository<BlogPost, Long> {
}
