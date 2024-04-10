package com.pet.repository;

import com.pet.dto.response.Statistic;
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

    @Query(value = "SELECT thang, SUM(CASE WHEN active = 1 and cancel = 0 THEN 1 ELSE 0 END) AS so_luong_da_ban,SUM(CASE WHEN cancel = 1 and active = 0 THEN 1 ELSE 0 END) AS so_luong_da_huy " +
            "FROM ( SELECT DATE_FORMAT(b.updated_at, '%m-%Y') AS thang, active, cancel FROM Bill b ORDER BY thang DESC ) AS subquery GROUP BY thang ORDER BY thang ASC LIMIT 8;", nativeQuery = true)
    List<Object[]> statisticBill();
}
