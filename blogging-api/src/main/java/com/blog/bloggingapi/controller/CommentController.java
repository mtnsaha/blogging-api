package com.blog.bloggingapi.controller;

import com.blog.bloggingapi.payload.ApiResponse;
import com.blog.bloggingapi.payload.CommentDto;
import com.blog.bloggingapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){

        CommentDto createdComment  = this.commentService.createComment(commentDto,postId);

        return  new ResponseEntity<CommentDto>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentsId}")
    ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentsId){

        this.commentService.deleteComment(commentsId);

      return new ResponseEntity<ApiResponse>(new ApiResponse("Deleted comment",true),HttpStatus.OK);
    }


}
