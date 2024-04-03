package com.pet.service.impl;

import com.pet.dto.response.CategoryResponse;
import com.pet.entity.Category;
import com.pet.exception.ExistedCategoryException;
import com.pet.exception.NotFoundException;
import com.pet.repository.ICategoryRepository;
import com.pet.service.ICategoryService;
import com.pet.service.base.IMessageService;
import com.pet.utils.MapperUtils;
import com.pet.utils.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pet.constant.MessageConstant.*;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private IMessageService messageService;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public Category addCategory(String categoryName, Long parentId) {
        String slug = UrlUtil.generateSlug(categoryName);
        Optional<Category> dataCategory = categoryRepository.findCategoryBySlug(slug);
        if (dataCategory.isPresent()) {
            throw new ExistedCategoryException(messageService.getMessage(EXISTED_CATEGORY_EXCEPTION));
        }
        if (parentId == 0) {
            return categoryRepository.save(
                    Category.builder()
                            .categoryName(categoryName)
                            .slug(slug)
                            .active(true)
                            .parentId(0L)
                            .level((short) 0)
                            .build());
        } else {
            Category parentCategory = checkCategory(parentId);
            return categoryRepository.save(
                    Category.builder()
                            .categoryName(categoryName)
                            .slug(slug)
                            .active(true)
                            .parentId(parentCategory.getId())
                            .level((short) (parentCategory.getLevel() + 1))
                            .build());
        }
    }

    @Override
    public List<CategoryResponse> getAllCategoriesTree() {
        List<CategoryResponse> responses = new ArrayList<>();
        List<Category> rootCategories = categoryRepository.findCategoriesByParentIdAndLevelAndActiveIsTrue(0L, (short) 0);
        rootCategories.forEach(
                root -> {
                    List<Category> childCategories = categoryRepository.findCategoriesByParentIdAndLevelAndActiveIsTrue(root.getId(), (short) 1);
                    if (!childCategories.isEmpty()) {
                        responses.add(CategoryResponse.EntityToResponse(root, MapperUtils.entitiesToDTOs(childCategories, CategoryResponse.class)));
                    } else {
                        responses.add(CategoryResponse.EntityToResponse(root, null));
                    }
                }
        );
        return responses;
    }

    @Override
    public Category checkCategory(Long categoryId) {
        return categoryRepository.findCategoryById(categoryId).orElseThrow(
                () -> new NotFoundException(messageService.getMessage(NOTFOUND_CATEGORY_EXCEPTION))
        );
    }

    @Override
    public CategoryResponse getCategoryBySlug(String slug) {
        Category category = categoryRepository.findCategoryBySlug(slug).orElseThrow(
                () -> new NotFoundException(messageService.getMessage(NOTFOUND_CATEGORY_EXCEPTION))
        );
        return MapperUtils.entityToDTO(category, CategoryResponse.class);
    }

    @Override
    public List<CategoryResponse> getAllCategoriesStore() {
        List<CategoryResponse> responses = new ArrayList<>();
        List<Category> rootCategories = categoryRepository.findCategoriesByParentIdAndLevelAndActiveIsTrue(2L, (short) 1);
        rootCategories.forEach(
                root -> {
                    responses.add(CategoryResponse.EntityToResponse(root, null));
                }
        );
        return responses;
    }


}
