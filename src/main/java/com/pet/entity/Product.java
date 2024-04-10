package com.pet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    private Long price;

    private Long priceSale;

    private Integer quantity;

    private Integer sale;


    private String image;

    @Column(name = "product_description")
    private String productDescription;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Long bought;

    private String slug;

    @Column(name = "attribute_name")
    private String attributeName;

}
