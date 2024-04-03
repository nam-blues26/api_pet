package com.pet.dto.response;

import com.pet.entity.Category;
import com.pet.entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private Long id;
    private String categoryName;
    private String slug;
    private List<CategoryResponse> childCategories;

    public static CategoryResponse EntityToResponse(Category category, List<CategoryResponse> categories){
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .slug(category.getSlug())
                .childCategories(categories)
                .build();
    }
}
