package com.example.project3.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AccountTransferAmountDTOIn {

    private Integer id;
    @NotEmpty(message = "Error: to Account Number is empty")
    @Pattern(regexp = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$" , message = "Error: to Account Number Must follow a specific format (e.g., \"XXXX-XXXX-XXXX-XXXX\")")
    private String toAccountNumber;
    @NotNull(message = "Error:amount is null")
    @Positive(message = "Error: amount must be positive")
    private Double amount;
}
