package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.CustomerDTOIn;
import com.example.project3.DTO.CustomerDTOOut;
import com.example.project3.Model.Customer;
import com.example.project3.Model.MyUser;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;


    public CustomerDTOOut getMyProfile(Integer userId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null ) throw new ApiException("Error: user not found");

        return convertCustomerToDTO(myUser.getCustomer());

    }

    public void registerCustomer(CustomerDTOIn customerDTOIn){

        MyUser myUser = new MyUser();

        myUser.setUsername(customerDTOIn.getUsername());
        myUser.setPassword(new BCryptPasswordEncoder().encode(customerDTOIn.getPassword()));
        myUser.setName(customerDTOIn.getName());
        myUser.setEmail(customerDTOIn.getEmail());
        myUser.setRole("CUSTOMER");
        myUser = authRepository.save(myUser);

        Customer customer = new Customer();

        customer.setPosition(customerDTOIn.getPosition());
        customer.setSalary(customerDTOIn.getSalary());
        customer.setMyUser(myUser);
        customerRepository.save(customer);
    }

    public void updateCustomer(Integer userId , CustomerDTOIn customerDTOIn){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null ) throw new ApiException("Error: user not found");

        myUser.setUsername(customerDTOIn.getUsername());
        myUser.setPassword(new BCryptPasswordEncoder().encode(customerDTOIn.getPassword()));
        myUser.setName(customerDTOIn.getName());
        myUser.setEmail(customerDTOIn.getEmail());
        myUser = authRepository.save(myUser);

        Customer customer = myUser.getCustomer();

        customer.setPosition(customerDTOIn.getPosition());
        customer.setSalary(customerDTOIn.getSalary());
        customerRepository.save(customer);
    }

    public CustomerDTOOut convertCustomerToDTO(Customer customer){
        return new CustomerDTOOut(customer.getMyUser().getUsername(),customer.getMyUser().getName(),customer.getMyUser().getEmail(),customer.getPosition(), customer.getSalary());
    }
}
