package com.pet.controller;

import com.pet.dto.request.AuthRequest;
import com.pet.dto.request.ProductRequest;
import com.pet.dto.response.ProductResponse;
import com.pet.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${project.pet.version.v1}/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody AuthRequest request) {
        return new ResponseEntity<>(userService.login(request), HttpStatus.OK);
    }
}
