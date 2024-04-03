package com.pet.repository;

import com.pet.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryBySlug(String slug);
    Optional<Category> findCategoryById(Long id);
    List<Category> findCategoriesByParentIdAndLevelAndActiveIsTrue(Long parentId, Short level);
}
