package io.regent.blogrestapi.contoller.v1;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.regent.blogrestapi.contoller.BlogsMapping;
import io.regent.blogrestapi.dtos.ListPostDTO;
import io.regent.blogrestapi.dtos.PagedListDTO;
import io.regent.blogrestapi.dtos.PostDTO;
import io.regent.blogrestapi.service.api.PostServiceApi;
import lombok.RequiredArgsConstructor;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 16/10/2022
 */

@RequiredArgsConstructor
@RestController
@RequestMapping(value = BlogsMapping.POST_RESOURCE_V1, produces = "application/json")
public class PostController {

    private final PostServiceApi postServiceApi;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody final PostDTO postDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postServiceApi.createPost(postDTO));
    }

    @GetMapping
    public PagedListDTO<Object> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) final int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) final int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) final String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) final String sortDir
    ) {
        return postServiceApi.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(value = "id") final Long id) {
        return ResponseEntity.ok(postServiceApi.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable("id") final Long id, @RequestBody final PostDTO postDTO) {
        return ResponseEntity.ok(postServiceApi.updatePostById(id, postDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<String> deletePost(@PathVariable("id") final Long id) {
        postServiceApi.deletePostById(id);
        return ResponseEntity.status(NO_CONTENT).body("Post deleted successfully");
    }
}

