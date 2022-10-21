package io.regent.blogrestapi.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.regent.blogrestapi.entity.BlogUser;

public interface BlogUserRepository extends JpaRepository<BlogUser, Long> {
    Optional<BlogUser> findBlogUserByUsernameAndPassword(String username, String password);
    Optional<BlogUser> findByEmail(String email);
    Optional<BlogUser> findByUsernameOrEmail(String username, String email);
    Optional<BlogUser> findByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
