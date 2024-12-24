package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import com.example.project3.DTO.EmployeeDTOIn;
import com.example.project3.Model.MyUser;
import com.example.project3.Repository.EmployeeRepository;
import com.example.project3.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/get-employee-profile")
    public ResponseEntity getEmployeeProfile(@AuthenticationPrincipal MyUser myUser ){
        return ResponseEntity.ok(employeeService.getMyProfile(myUser.getId()));
    }

    @PostMapping("/register-employee")
    public ResponseEntity registerEmployee(@RequestBody @Valid EmployeeDTOIn employeeDTOIn){
        employeeService.registerEmployee(employeeDTOIn);
        return ResponseEntity.status(201).body(new ApiResponse("registered successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity updateEmployee(@AuthenticationPrincipal MyUser myUser , @RequestBody @Valid EmployeeDTOIn employeeDTOIn ){
        employeeService.updateEmployee(myUser.getId(),employeeDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }
}
