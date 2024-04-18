package com.pet.repository;

import com.pet.entity.Category;
import com.pet.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByCategory(Category category);

    List<Product> findByOrderByIdDesc();

    Optional<Product> findProductById(Long id);

    Optional<Product> findProductBySlug(String slug);

    void deleteProductBySlug(String slug);

    //    @Query(value = "SELECT * FROM PRODUCT p WHERE p.bought >= 0 Limit 5",
//            nativeQuery = true)
    List<Product> findTop5ByOrderByBoughtDesc();

//    @Query(value = "SELECT * FROM PRODUCT p Order By p.updated_at desc Limit 5", nativeQuery = true)
    List<Product> findTop5ByOrderByUpdatedAtDesc();

    @Query(value = "SELECT * FROM PRODUCT p WHERE p.product_name LIKE %?1% ORDER BY p.updated_at DESC", nativeQuery = true)
    List<Product> searchByKeyword(String keyword);

    @Query(value = "SELECT * FROM PRODUCT p WHERE p.category_id = :category_id ORDER BY p.updated_at DESC", nativeQuery = true)
    List<Product> searchByCate(@Param("category_id") Long category_id);

    @Query(value = "SELECT * FROM PRODUCT p WHERE p.product_name LIKE %?1% AND p.category_id = ?2 ORDER BY p.updated_at DESC", nativeQuery = true)
    List<Product> searchByKeywordAndCate(String keyword, Long category_id);

}
