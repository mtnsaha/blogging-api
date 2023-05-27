package com.blog.bloggingapi.service;

import com.blog.bloggingapi.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer postId);

    void deleteComment(Integer commentId);

   /* CommentDto createComment(CommentDto commentDto, Integer postId);
        void deleteComment(Integer commentId);*/
}
