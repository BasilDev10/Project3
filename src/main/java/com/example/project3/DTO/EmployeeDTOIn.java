package com.example.project3.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmployeeDTOIn {



    @NotEmpty(message = "Error:username is empty")
    @Size(min = 4, max = 10 , message = "Error:username length must between 4 and 10 ")
    private String username;

    @NotEmpty(message = "Error:password is empty")
    @Size(min = 6 , message = "Error:password length must be at least 6 characters ")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$" , message = "Password is invalid")
    private String password;

    @NotEmpty(message = "Error:name is empty")
    @Size(min = 2, max = 20 , message = "Error:name length must between 2 and 20 ")
    private String name;

    @NotEmpty(message = "Error:email is empty")
    @Email(message = "Error:email Must be a valid email format.")
    private String email;

    @NotNull(message = "Error: phone number is null")
    @Pattern(regexp = "^05\\d{8}$" , message = "Error: The phone number must start with 05.and 10 digits")
    private String phoneNumber;
}
