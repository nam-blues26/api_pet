package com.pet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest {
    private String customerName;
    private String phoneNumber;
    private String address;
    private Long totalPrice;
    private String typePayment;
    private String note;
    private List<BillDetailRequest> billDetails;
}
