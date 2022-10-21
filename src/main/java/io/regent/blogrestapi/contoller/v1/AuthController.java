package io.regent.blogrestapi.contoller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.regent.blogrestapi.contoller.BlogsMapping;
import io.regent.blogrestapi.dtos.LoginDto;
import io.regent.blogrestapi.dtos.RegisterDTO;
import io.regent.blogrestapi.jwt.JWTAuthResponse;
import io.regent.blogrestapi.jwt.JWTTokenProvider;
import io.regent.blogrestapi.service.api.BlogUserServiceApi;
import lombok.RequiredArgsConstructor;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 20/10/2022
 */

@RestController
@RequestMapping(BlogsMapping.AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final BlogUserServiceApi userServiceApi;


    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody final LoginDto loginDto) {
        final JWTAuthResponse jwtAuthResponse = userServiceApi.authenticateUser(loginDto);
        return ResponseEntity.ok(jwtAuthResponse);
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody final RegisterDTO signupDetails) {
        // add check if usernameOrEmailExists
        final UserDto registerUser = userServiceApi.registerUser(signupDetails);
        return new ResponseEntity<>(registerUser.response(), registerUser.status());
    }
}
