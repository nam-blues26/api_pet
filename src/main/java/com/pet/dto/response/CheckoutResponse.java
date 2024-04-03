package com.pet.dto.response;

import com.pet.entity.BillDetail;
import com.pet.entity.User;
import com.pet.entity.enums.PaymentEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponse {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String customerName;
    private String phoneNumber;
    private Long totalPrice;
    private String address;
    private String typePayment;
    private Boolean active;
    private Boolean cancel;
    private List<BillDetail> billDetails;
}
