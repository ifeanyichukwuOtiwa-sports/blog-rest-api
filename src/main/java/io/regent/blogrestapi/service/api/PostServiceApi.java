package io.regent.blogrestapi.service.api;

import java.util.List;

import io.regent.blogrestapi.dtos.ListPostDTO;
import io.regent.blogrestapi.dtos.PagedListDTO;
import io.regent.blogrestapi.dtos.PostDTO;
import io.regent.blogrestapi.entity.BlogPost;

public interface PostServiceApi {
    PostDTO createPost(final PostDTO postDTO);

    ListPostDTO getAllPosts();

    PostDTO getPostById(final Long id);

    BlogPost getBlogPostByID(Long id);

    PostDTO updatePostById(Long id, final PostDTO postDTO);

    void deletePostById(Long id);

    PagedListDTO<Object> getAllPosts(int pageNo, int pageSize, final String sortBy, final String sortDir);
}
