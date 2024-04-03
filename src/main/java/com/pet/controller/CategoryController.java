package com.pet.controller;

import com.pet.dto.request.CategoryRequest;
import com.pet.dto.response.CategoryResponse;
import com.pet.entity.Category;
import com.pet.service.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${project.pet.version.v1}/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @PostMapping("/add-category")
    public ResponseEntity<Category> addCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        Category response =  categoryService.addCategory(categoryRequest.getCategoryName(), categoryRequest.getParentId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CategoryResponse>> getAll(){
        return new ResponseEntity<>(categoryService.getAllCategoriesTree(), HttpStatus.OK);
    }


    @GetMapping("/get-category-store")
    public ResponseEntity<List<CategoryResponse>> getCategoriesStore(){
        return new ResponseEntity<>(categoryService.getAllCategoriesStore(), HttpStatus.OK);
    }

    @GetMapping("/get/{slug}")
    public ResponseEntity<CategoryResponse> getCategoryBySlug(@PathVariable String slug){
        return new ResponseEntity<>(categoryService.getCategoryBySlug(slug), HttpStatus.OK);
    }
}
