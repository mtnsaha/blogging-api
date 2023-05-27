package com.blog.bloggingapi.service.impl;

import com.blog.bloggingapi.entities.Comment;
import com.blog.bloggingapi.entities.Post;
import com.blog.bloggingapi.exception.ResourceNotFoundException;
import com.blog.bloggingapi.payload.CommentDto;
import com.blog.bloggingapi.repository.CommentRepository;
import com.blog.bloggingapi.repository.PostRepository;
import com.blog.bloggingapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
   private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id", postId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);

        Comment saveComment = this.commentRepository.save(comment);

         return this.modelMapper.map(saveComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "comment id", commentId));

this.commentRepository.delete(comment);
    }
}
