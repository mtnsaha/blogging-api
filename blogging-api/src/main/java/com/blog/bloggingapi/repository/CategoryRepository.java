package com.blog.bloggingapi.repository;


import com.blog.bloggingapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
