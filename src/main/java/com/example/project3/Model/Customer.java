package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Customer {

    @Id
    private Integer id;

    @NotEmpty(message = "Error: position is empty")
    @Column(nullable = false)
    private String position;

    @NotNull(message = "Error: salary is empty")
    @Positive(message = "Error: salary must be positive ")
    @Column(nullable = false)
    private Double salary;

    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser myUser;

    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "customer")
    private Set<Account> accounts ;
}
