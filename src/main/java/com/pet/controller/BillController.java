package com.pet.controller;

import com.pet.dto.request.CategoryRequest;
import com.pet.dto.request.CheckoutRequest;
import com.pet.dto.response.CheckoutResponse;
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

    @GetMapping("/get/{id}")
    public ResponseEntity<CheckoutResponse> getBill(@PathVariable Long id){
        return new ResponseEntity<>(billService.getBillById(id), HttpStatus.OK);
    }
    @GetMapping("/active/{id}")
    public ResponseEntity<Boolean> acceptBill(@PathVariable Long id){
        return new ResponseEntity<>(billService.acceptBill(id), HttpStatus.OK);
    }

    @GetMapping("/cancel/{id}")
    public ResponseEntity<Boolean> cancelBill(@PathVariable Long id){
        return new ResponseEntity<>(billService.cancelBill(id), HttpStatus.OK);
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<CheckoutResponse>> getBillAll(){
        return new ResponseEntity<>(billService.getAll(), HttpStatus.OK);
    }
}
