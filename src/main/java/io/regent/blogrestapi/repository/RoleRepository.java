package io.regent.blogrestapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.regent.blogrestapi.entity.Role;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 20/10/2022
 */

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
