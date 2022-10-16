package io.regent.blogrestapi.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 16/10/2022
 */
@AllArgsConstructor
@Getter
public class ListPostDTO {
    List<PostDTO> posts;
}
