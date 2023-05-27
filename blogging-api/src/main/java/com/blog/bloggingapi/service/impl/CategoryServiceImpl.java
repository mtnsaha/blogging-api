package com.blog.bloggingapi.service.impl;


import com.blog.bloggingapi.entities.Category;
import com.blog.bloggingapi.exception.ResourceNotFoundException;
import com.blog.bloggingapi.payload.CategoryDto;
import com.blog.bloggingapi.repository.CategoryRepository;
import com.blog.bloggingapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDTO) {

        Category category = this.modelMapper.map(categoryDTO, Category.class);
        Category savecategory = this.categoryRepository.save(category);
        return this.modelMapper.map(savecategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDTO, Integer categoryId) {
        Category cat = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));

        cat.setCategoryTitle(categoryDTO.getCategoryTitle());
        cat.setDescription(categoryDTO.getDescription());
        Category updatedCat = this.categoryRepository.save(cat);

        return this.modelMapper.map(updatedCat, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));
        this.categoryRepository.delete(category);

    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));
        CategoryDto categoryDTO = this.modelMapper.map(category, CategoryDto.class);

        return categoryDTO;
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = this.categoryRepository.findAll();
        List<CategoryDto> categoriesDto = categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        return categoriesDto;
    }
}
