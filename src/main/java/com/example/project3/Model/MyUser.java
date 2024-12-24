package com.example.project3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class MyUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Error:username is empty")
    @Size(min = 4, max = 10 , message = "Error:username length must between 4 and 10 ")
    @Column(nullable = false, unique = true)
    private String username;

    @NotEmpty(message = "Error:password is empty")
    @Size(min = 6 , message = "Error:password length must be at least 6 characters ")
    @Column(nullable = false)
    private String password;

    @NotEmpty(message = "Error:name is empty")
    @Size(min = 2, max = 20 , message = "Error:name length must between 2 and 20 ")
    @Column(nullable = false)
    private String name;

    @NotEmpty(message = "Error:email is empty")
    @Email(message = "Error:email Must be a valid email format.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty(message = "Error:role is empty")
    @Pattern(regexp = "CUSTOMER|EMPLOYEE|ADMIN" , message = "role Must be either CUSTOMER , EMPLOYEE or ADMIN only")
    @Column(nullable = false)
    private String role;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "myUser")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "myUser")
    private Employee employee;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
