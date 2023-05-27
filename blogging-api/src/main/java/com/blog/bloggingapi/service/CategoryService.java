package com.blog.bloggingapi.service;


import com.blog.bloggingapi.payload.CategoryDto;

import java.util.List;

public interface CategoryService {

     CategoryDto createCategory(CategoryDto categoryDTO);

     CategoryDto updateCategory(CategoryDto categoryDTO, Integer categoryId);

    void deleteCategory(Integer categoryId);

     CategoryDto getCategory(Integer categoryId);

  List<CategoryDto> getAllCategory();


}
