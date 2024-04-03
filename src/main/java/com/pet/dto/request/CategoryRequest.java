package com.pet.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pet.constant.SwaggerConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "{category.name.not.blank}")
    @JsonProperty("category_name")
    @Schema(example = SwaggerConstant.CATEGORY_NAME)
    private String categoryName;

    @JsonProperty("parent_id")
    private Long parentId;
}
