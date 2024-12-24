package com.example.project3.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AccountDTOIn {

    private Integer id;

    @NotEmpty(message = "Error: account number is empty")
    @Pattern(regexp = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$" , message = "Error: accountNumber Must follow a specific format (e.g., \"XXXX-XXXX-XXXX-XXXX\")")
    private String accountNumber;

}
