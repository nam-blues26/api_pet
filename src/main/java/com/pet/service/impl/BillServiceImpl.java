package com.pet.service.impl;

import com.pet.dto.request.BillDetailRequest;
import com.pet.dto.request.CheckoutRequest;
import com.pet.dto.response.CheckoutResponse;
import com.pet.dto.response.Statistic;
import com.pet.entity.Bill;
import com.pet.entity.BillDetail;
import com.pet.entity.Product;
import com.pet.entity.User;
import com.pet.entity.enums.PaymentEnum;
import com.pet.exception.InsufficientQuantityException;
import com.pet.exception.NotFoundException;
import com.pet.repository.IBillDetailRepository;
import com.pet.repository.IBillRepository;
import com.pet.repository.IProductRepository;
import com.pet.repository.IUserRepository;
import com.pet.service.IBillService;
import com.pet.service.base.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pet.constant.MessageConstant.*;

@Service
public class BillServiceImpl implements IBillService {
    @Autowired
    private IProductRepository productRepository;


    @Autowired
    private IBillRepository billRepository;

    @Autowired
    private IBillDetailRepository billDetailRepository;
    @Autowired
    private IMessageService messageService;
    @Override
    public Boolean addBill(CheckoutRequest request) {
        Map<Long, Product> mapProducts = new HashMap<>();
        for (BillDetailRequest b : request.getBillDetails()) {
            Product product = productRepository.findProductById(b.getId()).orElseThrow(
                    () -> new NotFoundException(messageService.getMessage(NOTFOUND_PRODUCT_EXCEPTION))
            );
            if (product.getQuantity() < b.getAddedQuantity()) {
                throw new InsufficientQuantityException(messageService.getMessage(INSUFFICIENT_QUANTITY_EXCEPTION));
            }else {
                mapProducts.put(product.getId(),product);
            }
        }
        Bill bill = new Bill();
        bill.setActive(false);
        bill.setCancel(false);
        bill.setCustomerName(request.getCustomerName());
        bill.setPhoneNumber(request.getPhoneNumber());
        bill.setAddress(request.getAddress());
        bill.setTypePayment(PaymentEnum.valueOf(request.getTypePayment()));
        bill.setNote(request.getNote());
        bill.setTotalPrice(request.getTotalPrice());
        bill = billRepository.saveAndFlush(bill);
        for (BillDetailRequest b : request.getBillDetails()) {
            Product product = mapProducts.get(b.getId());
            if (product.getQuantity() >= b.getAddedQuantity()){
                BillDetail billDetail = new BillDetail();
                billDetail.setBill(bill);
                billDetail.setQuantity(b.getAddedQuantity());
                billDetail.setPrice(product.getPriceSale() * b.getAddedQuantity());
                billDetail.setProduct(product);
                billDetail.setAttributeName(b.getAttributeName());
                billDetailRepository.save(billDetail);
                product.setQuantity(product.getQuantity()-b.getAddedQuantity());
                product.setBought(product.getBought()+b.getAddedQuantity());
                productRepository.save(product);
            }
        }
        return true;
    }

    @Override
    public CheckoutResponse getBillById(Long id) {
        Bill b =  billRepository.findBillById(id);
        CheckoutResponse checkoutResponse = new CheckoutResponse();
        checkoutResponse.setId(b.getId());
        checkoutResponse.setCreatedAt(b.getCreatedAt());
        checkoutResponse.setUpdatedAt(b.getUpdatedAt());
        checkoutResponse.setCustomerName(b.getCustomerName());
        checkoutResponse.setPhoneNumber(b.getPhoneNumber());
        checkoutResponse.setAddress(b.getAddress());
        checkoutResponse.setActive(b.getActive());
        checkoutResponse.setCancel(b.getCancel());
        checkoutResponse.setTypePayment(b.getTypePayment().name());
        checkoutResponse.setTotalPrice(b.getTotalPrice());
        checkoutResponse.setNote(b.getNote());
        checkoutResponse.setBillDetails(billDetailRepository.findBillDetailsByBill(b));
        return checkoutResponse;
    }

    @Override
    public Boolean acceptBill(Long id) {
        Bill bill = billRepository.findBillById(id);
        bill.setActive(true);
        bill.setCancel(false);
        billRepository.save(bill);
        return true;
    }

    @Override
    public Boolean cancelBill(Long id) {
        Bill bill = billRepository.findBillById(id);
        bill.setActive(false);
        bill.setCancel(true);
        List<BillDetail> billDetails = billDetailRepository.findBillDetailsByBill(bill);
        billDetails.forEach(b -> {
            Product product = productRepository.findProductById(b.getProduct().getId()).orElseThrow(
                    () -> new NotFoundException(messageService.getMessage(NOTFOUND_PRODUCT_EXCEPTION))
            );
            product.setBought(product.getBought()-b.getQuantity());
            product.setQuantity(product.getQuantity()+b.getQuantity());
            productRepository.save(product);
        });
        billRepository.save(bill);
        return true;
    }

    @Override
    public List<CheckoutResponse> getBillsUnCheck() {
        List<CheckoutResponse> checkoutResponseList = new ArrayList<>();
        List<Bill> bills = billRepository.findBillsUnCheckOrderByCreatedAtNative();
        bills.forEach(b -> {
            CheckoutResponse checkoutResponse = new CheckoutResponse();
            checkoutResponse.setId(b.getId());
            checkoutResponse.setCreatedAt(b.getCreatedAt());
            checkoutResponse.setUpdatedAt(b.getUpdatedAt());
            checkoutResponse.setCustomerName(b.getCustomerName());
            checkoutResponse.setPhoneNumber(b.getPhoneNumber());
            checkoutResponse.setAddress(b.getAddress());
            checkoutResponse.setActive(b.getActive());
            checkoutResponse.setCancel(b.getCancel());
            checkoutResponse.setNote(b.getNote());
            checkoutResponse.setTypePayment(b.getTypePayment().name());
            checkoutResponse.setTotalPrice(b.getTotalPrice());
            checkoutResponse.setBillDetails(billDetailRepository.findBillDetailsByBill(b));
            checkoutResponseList.add(checkoutResponse);
        });
        return checkoutResponseList;
    }

    @Override
    public List<CheckoutResponse> getBillsActive() {
        List<CheckoutResponse> checkoutResponseList = new ArrayList<>();
        List<Bill> bills = billRepository.findBillsActiveOrderByCreatedAtNative();
        bills.forEach(b -> {
            CheckoutResponse checkoutResponse = new CheckoutResponse();
            checkoutResponse.setId(b.getId());
            checkoutResponse.setCreatedAt(b.getCreatedAt());
            checkoutResponse.setUpdatedAt(b.getUpdatedAt());
            checkoutResponse.setCustomerName(b.getCustomerName());
            checkoutResponse.setPhoneNumber(b.getPhoneNumber());
            checkoutResponse.setAddress(b.getAddress());
            checkoutResponse.setActive(b.getActive());
            checkoutResponse.setCancel(b.getCancel());
            checkoutResponse.setNote(b.getNote());
            checkoutResponse.setTypePayment(b.getTypePayment().name());
            checkoutResponse.setTotalPrice(b.getTotalPrice());
            checkoutResponse.setBillDetails(billDetailRepository.findBillDetailsByBill(b));
            checkoutResponseList.add(checkoutResponse);
        });
        return checkoutResponseList;
    }

    @Override
    public List<CheckoutResponse> getBillsCancel() {
        List<CheckoutResponse> checkoutResponseList = new ArrayList<>();
        List<Bill> bills = billRepository.findBillsCancelOrderByCreatedAtNative();
        bills.forEach(b -> {
            CheckoutResponse checkoutResponse = new CheckoutResponse();
            checkoutResponse.setId(b.getId());
            checkoutResponse.setCreatedAt(b.getCreatedAt());
            checkoutResponse.setUpdatedAt(b.getUpdatedAt());
            checkoutResponse.setCustomerName(b.getCustomerName());
            checkoutResponse.setPhoneNumber(b.getPhoneNumber());
            checkoutResponse.setAddress(b.getAddress());
            checkoutResponse.setActive(b.getActive());
            checkoutResponse.setCancel(b.getCancel());
            checkoutResponse.setTypePayment(b.getTypePayment().name());
            checkoutResponse.setTotalPrice(b.getTotalPrice());
            checkoutResponse.setNote(b.getNote());
            checkoutResponse.setBillDetails(billDetailRepository.findBillDetailsByBill(b));
            checkoutResponseList.add(checkoutResponse);
        });
        return checkoutResponseList;
    }

    @Override
    public List<Statistic> statistic() {
        List<Object[]> results = billRepository.statisticBill();
        return results.stream()
                .map(obj -> new Statistic((String)obj[0], (BigDecimal) obj[1], (BigDecimal) obj[2]))
                .collect(Collectors.toList());
    }

}
