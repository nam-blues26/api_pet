package com.pet.service.impl;

import com.pet.dto.request.ProductRequest;
import com.pet.dto.request.SearchRequest;
import com.pet.dto.response.CategoryProductResponse;
import com.pet.dto.response.ProductResponse;
import com.pet.entity.Category;
import com.pet.entity.Product;
import com.pet.exception.NotFoundException;
import com.pet.repository.ICategoryRepository;
import com.pet.repository.IProductRepository;
import com.pet.service.ICategoryService;
import com.pet.service.IProductService;
import com.pet.service.base.IMessageService;
import com.pet.utils.ImageUtil;
import com.pet.utils.MapperUtils;
import com.pet.utils.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.pet.constant.MessageConstant.NOTFOUND_CATEGORY_EXCEPTION;
import static com.pet.constant.MessageConstant.NOTFOUND_PRODUCT_EXCEPTION;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IMessageService messageService;
    @Autowired
    private IProductRepository productRepository;



    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private ICategoryService categoryService;

    @Override
    public ProductResponse addProduct(ProductRequest request) {
        Product product = new Product();
        String slug = UrlUtil.generateSlug(request.getProductName());
        Category productCategory = categoryService.checkCategory(request.getCategory());
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setSale(request.getSale());
        product.setQuantity(request.getQuantity());
        product.setCategory(productCategory);
        product.setSlug(slug);
        product.setActive(true);
        product.setProductDescription(request.getProductDescription());
        long roundedPriceSale = Math.round(request.getPrice() * (100 - request.getSale()) / 100.0);
        product.setPriceSale(roundedPriceSale);
        product.setBought(0L);
        product.setAttributeName(request.getAttributeName());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        try {
            if (request.getFiles() != null){
                String filename = ImageUtil.storeFile(request.getFiles());
                product.setImage(filename);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return MapperUtils.entityToDTO(productRepository.save(product), ProductResponse.class);
    }

    @Override
    public ProductResponse updateProduct(String slug, ProductRequest request) {
        Product product = productRepository.findProductBySlug(slug).orElseThrow(
                () -> new NotFoundException(messageService.getMessage(NOTFOUND_PRODUCT_EXCEPTION))
        );
        Category productCategory = categoryService.checkCategory(request.getCategory());
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setSale(request.getSale());
        product.setQuantity(request.getQuantity());
        product.setCategory(productCategory);
        long roundedPriceSale = Math.round(request.getPrice() * (100 - request.getSale()) / 100.0);
        product.setPriceSale(roundedPriceSale);
        product.setBought(0L);
        product.setProductDescription(request.getProductDescription());
        product.setAttributeName(request.getAttributeName());
        product.setUpdatedAt(LocalDateTime.now());
        try {
            if (request.getFiles() != null){
                String filename = ImageUtil.storeFile(request.getFiles());
                product.setImage(filename);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return MapperUtils.entityToDTO(productRepository.save(product), ProductResponse.class);
    }

    @Override
    public CategoryProductResponse getProductsByCategory(String categorySlug) {
        Category category = categoryRepository.findCategoryBySlug(categorySlug).orElseThrow(
                () -> new NotFoundException(messageService.getMessage(NOTFOUND_CATEGORY_EXCEPTION))
        );
//        CategoryProductResponse response = CategoryProductResponse.builder()
//                .id(category.getId())
//                .categoryName(category.getCategoryName())
//                .slug(category.getSlug())
//                .products(MapperUtils.entitiesToDTOs(productRepository.findProductsByCategory(category), ProductResponse.class)).build();
        return CategoryProductResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .slug(category.getSlug())
                .products(MapperUtils.entitiesToDTOs(productRepository.findProductsByCategoryAndActiveIsTrue(category), ProductResponse.class)).build();
    }

    @Override
    public Product getProductBySlug(String slug) {
        return productRepository.findProductBySlug(slug).orElseThrow(
                () -> new NotFoundException(messageService.getMessage(NOTFOUND_PRODUCT_EXCEPTION))
        );
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findByActiveIsTrueOrderByIdDesc();
    }

    @Override
    public Boolean deleteProduct(Long id) {
        Product product = productRepository.findProductByIdAndActiveIsTrue(id).orElseThrow(
                () -> new NotFoundException(messageService.getMessage(NOTFOUND_PRODUCT_EXCEPTION))
        );
        product.setActive(false);
        productRepository.save(product);
        return true;
    }

    @Override
    public List<Product> getProductsBestSeller() {
        return productRepository.findTop5ByActiveIsTrueOrderByBoughtDesc();
    }

    @Override
    public List<Product> getProductsNew() {
        return productRepository.findTop5ByActiveIsTrueOrderByUpdatedAtDesc();
    }

    @Override
    public List<Product> search(SearchRequest request) {
        if (request.getKeyword() != null && request.getCategory_id() != 0){
            return productRepository.searchByKeywordAndCate(request.getKeyword(), request.getCategory_id());
        } else if (request.getKeyword() != null && request.getCategory_id() == 0) {
            return productRepository.searchByKeyword(request.getKeyword());
        } else if (request.getKeyword() == null && request.getCategory_id() != 0) {
            return productRepository.searchByCate(request.getCategory_id());
        }
        else {
            return productRepository.findByActiveIsTrueOrderByIdDesc();
        }
    }

}
