package com.pet.repository;

import com.pet.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findPostBySlug(String slug);

    @Override
    List<Post> findAll();


    List<Post> findByOrderByIdDesc();
}
