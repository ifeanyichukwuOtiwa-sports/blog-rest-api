package io.regent.blogrestapi.service.impl;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.regent.blogrestapi.contoller.v1.UserDto;
import io.regent.blogrestapi.dtos.LoginDto;
import io.regent.blogrestapi.dtos.RegisterDTO;
import io.regent.blogrestapi.entity.BlogUser;
import io.regent.blogrestapi.entity.Role;
import io.regent.blogrestapi.jwt.JWTAuthResponse;
import io.regent.blogrestapi.jwt.JWTTokenProvider;
import io.regent.blogrestapi.repository.BlogUserRepository;
import io.regent.blogrestapi.repository.RoleRepository;
import io.regent.blogrestapi.service.api.BlogUserServiceApi;
import lombok.RequiredArgsConstructor;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 20/10/2022
 */

@Service
@RequiredArgsConstructor
public class BlogUserServiceApiImpl implements BlogUserServiceApi {
    private final BlogUserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTTokenProvider jwtTokenProvider;


    @Override
    public UserDto registerUser(final RegisterDTO signupDetails) {
        if (userRepository.existsByUsername(signupDetails.username()).equals(Boolean.TRUE)) {
            return new UserDto("user is already taken", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signupDetails.email()).equals(Boolean.TRUE)) {
            return new UserDto("user is already taken", HttpStatus.BAD_REQUEST);
        }

        //create user Object
        BlogUser user = mapToBlogUser(signupDetails, "ROLE_ADMIN");

        final BlogUser savedUser = userRepository.save(user);
        return new UserDto("User %s registered successfully".formatted(savedUser.getId()), HttpStatus.OK);
    }

    @Override
    public JWTAuthResponse authenticateUser(final LoginDto loginDto) {
        final Authentication notAuthenticated =
                new UsernamePasswordAuthenticationToken(
                        loginDto.usernameOrEmail(),
                        loginDto.password()
                );
        final Authentication authenticated = authenticationManager.authenticate(notAuthenticated);

        SecurityContextHolder.getContext().setAuthentication(authenticated);

        String token = jwtTokenProvider.generateToken(authenticated);

        return new JWTAuthResponse(token, "Bearer");

    }

    private BlogUser mapToBlogUser(final RegisterDTO signupDetails, final String role) {
        final Role userRole = roleRepository.findByName(role).orElse(new Role(role));
        return BlogUser.builder()
                .username(signupDetails.username())
                .email(signupDetails.email())
                .name(signupDetails.name())
                .password(passwordEncoder.encode(signupDetails.password()))
                .roles(Set.of(userRole))
                .build();
    }
}
