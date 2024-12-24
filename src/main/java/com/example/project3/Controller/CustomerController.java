package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import com.example.project3.DTO.CustomerDTOIn;
import com.example.project3.Model.MyUser;
import com.example.project3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @GetMapping("/get-customer-profile")
    public ResponseEntity getCustomerProfile(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.ok(customerService.getMyProfile(myUser.getId()));
    }

    @PostMapping("/register-customer")
    public ResponseEntity registerCustomer(@RequestBody @Valid CustomerDTOIn customerDTOIn){
        customerService.registerCustomer(customerDTOIn);
        return ResponseEntity.status(201).body(new ApiResponse("registered successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal MyUser myUser , @RequestBody @Valid CustomerDTOIn customerDTOIn){
        customerService.updateCustomer(myUser.getId(),customerDTOIn);
        return ResponseEntity.ok(new ApiResponse("updated successfully"));
    }
}
