package com.pet.service;

import com.pet.dto.request.CheckoutRequest;
import com.pet.dto.response.CheckoutResponse;
import com.pet.dto.response.Statistic;

import java.util.List;

public interface IBillService {
    public Boolean addBill(CheckoutRequest request);
    public CheckoutResponse getBillById(Long id);
    public Boolean acceptBill(Long id);
    public Boolean cancelBill(Long id);
    public List<CheckoutResponse> getBillsUnCheck();

    public List<CheckoutResponse> getBillsActive();

    public List<CheckoutResponse> getBillsCancel();

    public List<Statistic> statistic();

}
