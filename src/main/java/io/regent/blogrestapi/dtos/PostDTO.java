package io.regent.blogrestapi.dtos;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 16/10/2022
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO{
    private Long id;

    @NotEmpty
    @NotBlank
    @Size(min = 2, message = "title should have at least 2 characters")
    private String title;

    @NotBlank
    @NotNull
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDTO> comments;
}
