package com.pet.dto.response;

import com.pet.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String productName;

    private Long price;

    private Long priceSale;

    private Integer sale;

    private String slug;

    private Category category;

    private String image;
}
