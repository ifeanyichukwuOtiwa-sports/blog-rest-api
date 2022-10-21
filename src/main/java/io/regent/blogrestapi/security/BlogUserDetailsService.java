package io.regent.blogrestapi.security;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.regent.blogrestapi.entity.BlogUser;
import io.regent.blogrestapi.entity.Role;
import io.regent.blogrestapi.repository.BlogUserRepository;
import lombok.RequiredArgsConstructor;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 20/10/2022
 */

@Service
@RequiredArgsConstructor
public class BlogUserDetailsService implements UserDetailsService {

    private final BlogUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String usernameOrEmail) throws UsernameNotFoundException {
        final BlogUser blogUser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));
        return new User(
                blogUser.getEmail(),
                blogUser.getPassword(),
                mapRolesToAuthorities(blogUser.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(final Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }
}
