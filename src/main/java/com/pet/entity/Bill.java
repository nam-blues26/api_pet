package com.pet.entity;

import com.pet.entity.enums.PaymentEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "bill")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bill extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "total_price")
    private Long totalPrice;

    private String address;

    private String note;

    private Boolean active;

    private Boolean cancel;


    @Enumerated(EnumType.STRING)
    private PaymentEnum typePayment;

}
