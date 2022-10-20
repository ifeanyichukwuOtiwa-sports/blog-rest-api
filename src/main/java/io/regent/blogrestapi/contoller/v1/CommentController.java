package io.regent.blogrestapi.contoller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.regent.blogrestapi.contoller.BlogsMapping;
import io.regent.blogrestapi.dtos.CommentDTO;
import io.regent.blogrestapi.service.api.CommentServiceApi;
import lombok.RequiredArgsConstructor;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 17/10/2022
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(BlogsMapping.COMMENT_RESOURCE_V1)
public class CommentController {
    private final CommentServiceApi commentServiceApi;

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") long postId,
                                                    @Valid @RequestBody CommentDTO comment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentServiceApi.createComment(postId, comment));
    }

    @GetMapping
    public List<CommentDTO> getCommentsByPostId(@PathVariable("postId") long postId) {
        return commentServiceApi.getCommentsByPostId(postId);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable("postId") long postId, @PathVariable("commentId") long commentId) {
        return ResponseEntity.ok(commentServiceApi.getCommentById(postId, commentId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId,
            @Valid @RequestBody CommentDTO comment
    ) {
        final CommentDTO commentDTO = commentServiceApi.updateComment(postId, commentId, comment);
        return ResponseEntity.ok(commentDTO);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId, @PathVariable("commentId") long commentId) {
        commentServiceApi.deleteComment(postId, commentId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment Deleted Successfully");
    }
}
