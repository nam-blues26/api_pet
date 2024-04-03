package com.pet.repository;

import com.pet.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IColorRepository extends JpaRepository<Color, Long> {
    List<Color> findAll();
    Color findColorById(Long id);
}
