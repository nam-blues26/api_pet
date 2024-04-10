package com.pet.service;

import com.pet.dto.request.ProductRequest;
import com.pet.dto.request.SearchRequest;
import com.pet.dto.response.CategoryProductResponse;
import com.pet.dto.response.ProductResponse;
import com.pet.entity.Product;

import java.util.List;

public interface IProductService {
    ProductResponse addProduct(ProductRequest request);

    ProductResponse updateProduct(String slug, ProductRequest request);

    CategoryProductResponse getProductsByCategory(String categorySlug);

    Product getProductBySlug(String slug);

    List<Product> getAll();
    Boolean deleteProduct(Long id);

    List<Product> getProductsBestSeller();

    List<Product> getProductsNew();

    List<Product> search(SearchRequest request);
}
