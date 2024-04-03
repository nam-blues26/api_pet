package com.pet.repository;

import com.pet.entity.Bill;
import com.pet.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBillDetailRepository extends JpaRepository<BillDetail,Long> {
    List<BillDetail> findBillDetailsByBill(Bill bill);
}
