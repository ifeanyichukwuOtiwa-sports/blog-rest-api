package io.regent.blogrestapi.service.api;

import io.regent.blogrestapi.contoller.v1.UserDto;
import io.regent.blogrestapi.dtos.LoginDto;
import io.regent.blogrestapi.dtos.RegisterDTO;
import io.regent.blogrestapi.jwt.JWTAuthResponse;

public interface BlogUserServiceApi {
    UserDto registerUser(RegisterDTO signupDetails);

    JWTAuthResponse authenticateUser(LoginDto loginDto);
}
