package com.pet.dto.response;

import com.pet.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryProductResponse {
    private Long id;
    private String categoryName;
    private String slug;
    private List<ProductResponse> products;
}
