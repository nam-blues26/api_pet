package com.pet.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private String productName;
    private String productDescription;
    private Long price;

    private Integer quantity;

    private Integer sale;

    private String attributeName;


    private Long category;

    private MultipartFile files;
}
