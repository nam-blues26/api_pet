package com.pet.repository;

import com.pet.entity.Bill;
import com.pet.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBillRepository extends JpaRepository<Bill, Long> {
    Bill findBillById(Long id);

    @Query(value = "SELECT * FROM Bill b WHERE b.active = false and b.cancel = false Order By b.created_at desc", nativeQuery = true)
    List<Bill> findBillsUnCheckOrderByCreatedAtNative();

    @Query(value = "SELECT * FROM Bill b WHERE b.active = true and b.cancel = false Order By b.updated_at desc", nativeQuery = true)
    List<Bill> findBillsActiveOrderByCreatedAtNative();

    @Query(value = "SELECT * FROM Bill b WHERE b.cancel = true Order By b.updated_at desc", nativeQuery = true)
    List<Bill> findBillsCancelOrderByCreatedAtNative();
}
