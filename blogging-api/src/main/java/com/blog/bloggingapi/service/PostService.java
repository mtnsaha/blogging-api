package com.blog.bloggingapi.service;

import com.blog.bloggingapi.payload.PostDto;
import com.blog.bloggingapi.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto,Integer postId);

    void deletePost(Integer postId);

    PostResponse getAllPost(Integer pageNo, Integer pageSize, String sortBy, String sortDir);

    PostDto getPostById(Integer posId);

    List<PostDto> getPostByCategory(Integer categoryId);

    List<PostDto> getPostByUser(Integer userId);

    //search post by ketword
    List<PostDto> getSearchPost(String keyword);


}
