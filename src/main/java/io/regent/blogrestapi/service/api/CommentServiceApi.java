package io.regent.blogrestapi.service.api;

import java.util.List;

import io.regent.blogrestapi.dtos.CommentDTO;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 16/10/2022
 */

public interface CommentServiceApi {
    CommentDTO createComment(long postId, CommentDTO commentDto);

    List<CommentDTO> getCommentsByPostId(long postId);

    @SuppressWarnings("all")
//   todo PagedListDTO<Object> getCommentsByPostId(long postId, int pageSize, int pageNo, String sortBy, String sortOrder);

    CommentDTO getCommentById(Long postId, Long commentId);

    CommentDTO updateComment(Long postId, long commentId, CommentDTO commentRequest);

    void deleteComment(Long postId, Long commentId);
}
