package com.pet.controller;

import com.pet.dto.request.CategoryRequest;
import com.pet.dto.request.CheckoutRequest;
import com.pet.dto.response.CheckoutResponse;
import com.pet.dto.response.Statistic;
import com.pet.entity.Bill;
import com.pet.entity.Category;
import com.pet.service.IBillService;
import com.pet.service.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${project.pet.version.v1}/checkout")
public class BillController {
    @Autowired
    private IBillService billService;

    @PostMapping("/add-bill")
    public ResponseEntity<Boolean> addBill(@Valid @RequestBody CheckoutRequest checkoutRequest){
        return new ResponseEntity<>(billService.addBill(checkoutRequest), HttpStatus.CREATED);
    }
    @GetMapping("/active/{id}")
    public ResponseEntity<Boolean> acceptBill(@PathVariable Long id){
        return new ResponseEntity<>(billService.acceptBill(id), HttpStatus.OK);
    }

    @GetMapping("/cancel/{id}")
    public ResponseEntity<Boolean> cancelBill(@PathVariable Long id){
        return new ResponseEntity<>(billService.cancelBill(id), HttpStatus.OK);
    }
    @GetMapping("/get/un-check")
    public ResponseEntity<List<CheckoutResponse>> getBillsUncheck(){
        return new ResponseEntity<>(billService.getBillsUnCheck(), HttpStatus.OK);
    }

    @GetMapping("/get/active")
    public ResponseEntity<List<CheckoutResponse>> getBillsActive(){
        return new ResponseEntity<>(billService.getBillsActive(), HttpStatus.OK);
    }
    @GetMapping("/get/cancel")
    public ResponseEntity<List<CheckoutResponse>> getBillsCancel(){
        return new ResponseEntity<>(billService.getBillsCancel(), HttpStatus.OK);
    }

    @GetMapping("/get/statistic")
    public ResponseEntity<List<Statistic>> getStatistic(){
        return new ResponseEntity<>(billService.statistic(), HttpStatus.OK);
    }
}
