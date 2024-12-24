package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Error: account number is empty")
    @Pattern(regexp = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$" , message = "Error: accountNumber Must follow a specific format (e.g., \"XXXX-XXXX-XXXX-XXXX\")")
    @Column(nullable = false , unique = true)
    private String accountNumber;

    @NotNull(message = "Error:balance is null")
    @PositiveOrZero(message = "Error: balance must be positive or zero")
    @Column(columnDefinition = "DOUBLE not null default 0")
    private Double balance;

    @Column(columnDefinition = "BOOLEAN default 0")
    private Boolean active;

    @ManyToOne
    @JsonIgnore
    private Customer customer;
}
