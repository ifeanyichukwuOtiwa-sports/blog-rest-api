package io.regent.blogrestapi.dtos;

import lombok.Value;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 16/10/2022
 */

public record PostDTO(Long id, String title, String description, String content) {
}
