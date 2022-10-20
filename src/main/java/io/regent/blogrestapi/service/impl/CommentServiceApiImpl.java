package io.regent.blogrestapi.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.regent.blogrestapi.dtos.CommentDTO;
import io.regent.blogrestapi.dtos.PagedListDTO;
import io.regent.blogrestapi.entity.BlogPost;
import io.regent.blogrestapi.entity.Comment;
import io.regent.blogrestapi.interceptor.exception.BlogAPIException;
import io.regent.blogrestapi.interceptor.exception.ResourceNotFoundException;
import io.regent.blogrestapi.repository.CommentRepository;
import io.regent.blogrestapi.service.api.CommentServiceApi;
import io.regent.blogrestapi.service.api.PostServiceApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 16/10/2022
 */

@RequiredArgsConstructor
@Service
@Slf4j
public class CommentServiceApiImpl implements CommentServiceApi {

    private final CommentRepository commentRepository;
    private final PostServiceApi postServiceApi;

    @Override
    public CommentDTO createComment(final long postId, final CommentDTO commentDto) {
        BlogPost post = postServiceApi.getBlogPostByID(postId);
        log.info("post with id: {} found", post);
        Comment newComment = getNewCommentFromDTO(commentDto, post);
        final Comment savedComment = commentRepository.save(newComment);

        return mapToDTO(savedComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(final long postId) {
        final List<Comment> allById = commentRepository.findCommentByPostId(postId);
        return allById.stream().map(this::mapToDTO).toList();
    }


//    @Override

    public PagedListDTO<Object> getCommentsByPostId(final long postId, final int pageSize, final int pageNo, final String sortBy,
                                               final String sortOrder) {
        final Sort sorted = Sort.by(sortBy);
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.DESC.name()) ? sorted.ascending() : sorted.descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Comment> pagedComments = commentRepository.findAll(pageable);
        List<Comment> allComments = pagedComments.getContent();

        return PagedListDTO.builder()
                .contents(allComments)
                .last(pagedComments.isLast())
                .totalPages(pagedComments.getTotalPages())
                .totalElements(pagedComments.getTotalElements())
                .pageNo(pagedComments.getNumber())
                .pageSize(pagedComments.getSize()).build();
    }

    @Override
    public CommentDTO getCommentById(final Long postId, final Long commentId) {
        final Comment comment = verifyComment(postId, commentId);
        return mapToDTO(comment);
    }

    @Override
    public CommentDTO updateComment(final Long postId, final long commentId, final CommentDTO commentRequest) {
        final Comment comment = verifyComment(postId, commentId);

        comment.setBody(commentRequest.getBody());
        comment.setEmail(commentRequest.getEmail());
        comment.setName(commentRequest.getName());
        final Comment savedComment = commentRepository.save(comment);
        return mapToDTO(savedComment);
    }

    @Override
    public void deleteComment(final Long postId, final Long commentId) {
        final Comment comment = verifyComment(postId, commentId);
        log.info("{} found: >>> Deleting", comment);
        commentRepository.delete(comment);
    }

    private CommentDTO mapToDTO(final Comment savedComment) {
        return new CommentDTO(savedComment.getId(), savedComment.getName(), savedComment.getEmail(), savedComment.getBody());
    }

    private Comment getNewCommentFromDTO(final CommentDTO commentDto, final BlogPost post) {
        return new Comment(
                commentDto.getName(),
                commentDto.getEmail(),
                commentDto.getEmail(),
                post);
    }


    private Comment findGetCommentByID(final Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(
                "Comment", "id",
                commentId + ""));
    }

    private Comment verifyComment(final long postId, final long commentId) throws BlogAPIException {
        final BlogPost blogPost = postServiceApi.getBlogPostByID(postId);
        final Comment comment = findGetCommentByID(commentId);
        if (!Objects.equals(blogPost.getId(), comment.getPost().getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }
        return comment;
    }
}
