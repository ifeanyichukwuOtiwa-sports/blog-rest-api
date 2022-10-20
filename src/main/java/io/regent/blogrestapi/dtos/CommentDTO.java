package io.regent.blogrestapi.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 16/10/2022
 */

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "email should not be empty")
    @Email(regexp = ".+[@].+[\\.].+")
    private String email;

    @NotEmpty
    @Size(min = 10, message = "body should contain a minimum of 10 CHaracters")
    private String body;
}
