package com.pet.controller;

import com.pet.dto.request.ProductRequest;
import com.pet.dto.response.CategoryProductResponse;
import com.pet.dto.response.ProductResponse;
import com.pet.entity.Product;
import com.pet.service.IProductService;
import com.pet.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("${project.pet.version.v1}/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ProductResponse> addProduct(@ModelAttribute ProductRequest productRequest) {
        return new ResponseEntity<>(productService.addProduct(productRequest), HttpStatus.CREATED);
    }

    @GetMapping("/category/{categorySlug}")
    public ResponseEntity<CategoryProductResponse> getProductsByCategory(@PathVariable String categorySlug) {
        return new ResponseEntity<>(productService.getProductsByCategory(categorySlug), HttpStatus.OK);
    }

    @GetMapping("/get/{slug}")
    public ResponseEntity<Product> getProductBySlug(@PathVariable String slug) {
        return new ResponseEntity<>(productService.getProductBySlug(slug), HttpStatus.OK);
    }

    @PostMapping("/update/{slug}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String slug, @ModelAttribute ProductRequest productRequest) {
        return new ResponseEntity<>(productService.updateProduct(slug, productRequest), HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Product>> getAllProduct() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/best-seller")
    public ResponseEntity<List<Product>> getProductsBestSeller() {
        return new ResponseEntity<>(productService.getProductsBestSeller(), HttpStatus.OK);
    }
    @GetMapping("/get-new")
    public ResponseEntity<List<Product>> getProductsNew() {
        return new ResponseEntity<>(productService.getProductsNew(), HttpStatus.OK);
    }
    @GetMapping("/image/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName) {
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpeg").toUri()));
                //return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
