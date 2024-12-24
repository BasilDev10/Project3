package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Employee {

    @Id
    private Integer id;

    @NotNull(message = "Error: phone number is null")
    @Column(nullable = false)
    private String phoneNumber;

    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser myUser;
}
