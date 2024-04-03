package com.pet.repository;

import com.pet.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBannerRepository extends JpaRepository<Banner, Long> {

}
