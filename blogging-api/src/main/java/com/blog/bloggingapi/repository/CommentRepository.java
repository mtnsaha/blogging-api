package com.blog.bloggingapi.repository;

import com.blog.bloggingapi.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
