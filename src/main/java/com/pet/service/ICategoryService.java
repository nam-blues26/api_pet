package com.pet.service;

import com.pet.dto.response.CategoryResponse;
import com.pet.entity.Category;

import java.util.List;

public interface ICategoryService {
    Category addCategory(String categoryName, Long parentId);
    List<CategoryResponse> getAllCategoriesTree();
    Category checkCategory(Long categoryId);
    CategoryResponse getCategoryBySlug(String slug);

    List<CategoryResponse> getAllCategoriesStore();
}
