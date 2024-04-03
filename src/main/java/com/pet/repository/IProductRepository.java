package com.pet.repository;

import com.pet.entity.Category;
import com.pet.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {
    List<Product> findProductsByCategory(Category category);

    List<Product> findByOrderByIdDesc();

    Optional<Product> findProductById(Long id);

    Optional<Product> findProductBySlug(String slug);
    void deleteProductBySlug(String slug);

    @Query(value = "SELECT * FROM PRODUCT p WHERE p.bought >= 0 Limit 5",
            nativeQuery = true)
    List<Product> findProductBestSeller();

    @Query(value = "SELECT * FROM PRODUCT p Order By p.updated_at desc Limit 5", nativeQuery = true)
    List<Product> findProductOrderByUpdatedAtNative();
}
