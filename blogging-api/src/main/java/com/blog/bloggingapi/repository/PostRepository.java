package com.blog.bloggingapi.repository;

import com.blog.bloggingapi.entities.Category;
import com.blog.bloggingapi.entities.Post;
import com.blog.bloggingapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);
}
