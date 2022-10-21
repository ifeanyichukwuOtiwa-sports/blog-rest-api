package io.regent.blogrestapi.contoller.v1;

import org.springframework.http.HttpStatus;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 20/10/2022
 */

public record UserDto(String response, HttpStatus status) {
}
