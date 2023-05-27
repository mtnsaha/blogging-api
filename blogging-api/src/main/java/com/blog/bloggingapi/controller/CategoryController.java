package com.blog.bloggingapi.controller;

import com.blog.bloggingapi.payload.ApiResponse;
import com.blog.bloggingapi.payload.CategoryDto;
import com.blog.bloggingapi.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategories(@Valid @RequestBody CategoryDto categoryDTO){
        CategoryDto categoryDto1 = this.categoryService.createCategory(categoryDTO);
        return new ResponseEntity<CategoryDto>(categoryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategories(@Valid @RequestBody CategoryDto categoryDTO, @PathVariable Integer catId){
        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDTO,catId);
        return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
    }
@DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategories(@PathVariable Integer catId){
        this.categoryService.deleteCategory(catId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("categoies Deleted",true),HttpStatus.OK);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategories(@PathVariable Integer catId){
        CategoryDto categoryDto = this.categoryService.getCategory(catId);
        return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> allCategories = this.categoryService.getAllCategory();
        return ResponseEntity.ok(allCategories);
    }

}
