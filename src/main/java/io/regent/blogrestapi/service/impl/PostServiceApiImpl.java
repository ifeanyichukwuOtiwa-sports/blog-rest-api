package io.regent.blogrestapi.service.impl;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import io.regent.blogrestapi.dtos.ListPostDTO;
import io.regent.blogrestapi.dtos.PagedListDTO;
import io.regent.blogrestapi.dtos.PostDTO;
import io.regent.blogrestapi.entity.BlogPost;
import io.regent.blogrestapi.exception.ResourceNotFoundException;
import io.regent.blogrestapi.repository.PostRepository;
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
public class PostServiceApiImpl implements PostServiceApi {

    private final PostRepository postRepository;

    @Override
    public PostDTO createPost(final PostDTO postDTO) {
        //convert dto to entity
        BlogPost newPost = buildFromDTO(postDTO);

        BlogPost savedPost = postRepository.save(newPost);


        return this.mapToDTO(savedPost);
    }

    @Override
    public ListPostDTO getAllPosts() {
        final List<BlogPost> allPosts = postRepository.findAll();

        return new ListPostDTO(allPosts.stream().map(this::mapToDTO).toList());
    }

    @Override
    public PostDTO getPostById(final Long id) {
        final BlogPost blogPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        return mapToDTO(blogPost);
    }

    @Override
    public PostDTO updatePostById(final Long id, final PostDTO postDTO) {
        final BlogPost blogPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        blogPost.setTitle(postDTO.title());
        blogPost.setDescription(postDTO.description());
        blogPost.setContent(postDTO.content());

        final BlogPost updatedPost = postRepository.save(blogPost);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(final Long id) {
        final BlogPost post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id",
                id + ""));
        log.info("post: {} found", post);
        postRepository.deleteById(id);
    }

    @Override
    public PagedListDTO<Object> getAllPosts(final int pageNo, final int pageSize, final String sortBy, final String sortOrder) {
        final Sort sorted = Sort.by(sortBy);
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.DESC.name()) ? sorted.descending() : sorted.ascending();
        final Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        final Page<BlogPost> allPosts = postRepository.findAll(pageable);
        final List<BlogPost> posts = allPosts.getContent();
        final List<PostDTO> contents = posts.stream().map(this::mapToDTO).toList();

        return PagedListDTO.builder()
                .contents(contents)
                .pageNo(allPosts.getNumber())
                .pageSize(allPosts.getSize())
                .last(allPosts.isLast())
                .totalElements(allPosts.getTotalElements())
                .totalPages(allPosts.getTotalPages())
                .build();
    }


    private BlogPost buildFromDTO(final PostDTO postDTO) {
        return BlogPost.builder()
                .title(postDTO.title())
                .description(postDTO.description())
                .content(postDTO.content())
                .build();
    }

    private PostDTO mapToDTO(final BlogPost savedPost) {
        return new PostDTO(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getDescription(),
                savedPost.getContent()
        );
    }
}