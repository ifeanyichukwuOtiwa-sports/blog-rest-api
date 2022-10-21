package io.regent.blogrestapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.regent.blogrestapi.jwt.JWTAuthenticationEntryPoint;
import io.regent.blogrestapi.jwt.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 20/10/2022
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // provide method level security
@RequiredArgsConstructor
public class SecurityConfig /**
 extends WebSecurityConfigurerAdapter
 */
{

    public final JWTAuthenticationFilter authenticationFilter;
    private final BlogUserDetailsService blogUserDetailsService;
    private final JWTAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(blogUserDetailsService)
//                .passwordEncoder(getPasswordEncoder());
//
//    }

//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//
//                //jwt starts
//                .exceptionHandling()
//                .authenticationEntryPoint(authenticationEntryPoint)
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//
//
//                .authorizeRequests(
//                        authorize -> authorize
//                                .antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
//                                .antMatchers("/api/v1/auth/**").permitAll()
//                                .anyRequest().authenticated()
//                                .and()
//                )
//                .httpBasic();
//
//        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
//    }

    /**
     * </p>
     *
     * @Override
     * @Bean protected UserDetailsService userDetailsService() {
     * final UserDetails test = User.builder()
     * .username("test")
     * .password(getPasswordEncoder().encode("test"))
     * .roles("USER")
     * .build();
     * final UserDetails admin = User.builder()
     * .username("admin")
     * .password(getPasswordEncoder().encode("admin"))
     * .roles("ADMIN")
     * .build();
     * final UserDetails root = User.builder().username("root")
     * .password(getPasswordEncoder().encode("123456"))
     * .roles("ADMIN")
     * .build();
     * </p>
     * return new InMemoryUserDetailsManager(test, admin, root);
     * }
     */


    @Bean
    protected SecurityFilterChain secureFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests(authorize -> authorize.antMatchers("/api/v1/**").permitAll().antMatchers("/api/v1/auth/**").permitAll().anyRequest().authenticated());
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
