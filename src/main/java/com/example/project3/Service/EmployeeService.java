package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.CustomerDTOOut;
import com.example.project3.DTO.EmployeeDTOIn;
import com.example.project3.DTO.EmployeeDTOOut;
import com.example.project3.Model.Employee;
import com.example.project3.Model.MyUser;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;

    public EmployeeDTOOut getMyProfile(Integer userId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null ) throw new ApiException("Error: user not found");

        return convertEmployeeDTO(myUser.getEmployee());
    }

    public void registerEmployee(EmployeeDTOIn employeeDTOIn){
        MyUser myUser = new MyUser();

        myUser.setUsername(employeeDTOIn.getUsername());
        myUser.setPassword(new BCryptPasswordEncoder().encode(employeeDTOIn.getPassword()));
        myUser.setName(employeeDTOIn.getName());
        myUser.setEmail(employeeDTOIn.getEmail());
        myUser.setRole("EMPLOYEE");
        myUser = authRepository.save(myUser);

        Employee employee = new Employee();

        employee.setPhoneNumber(employeeDTOIn.getPhoneNumber());
        employee.setMyUser(myUser);
        employeeRepository.save(employee);
    }

    public void updateEmployee(Integer userId,EmployeeDTOIn employeeDTOIn){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null ) throw new ApiException("Error: user not found");

        myUser.setUsername(employeeDTOIn.getUsername());
        myUser.setPassword(new BCryptPasswordEncoder().encode(employeeDTOIn.getPassword()));
        myUser.setName(employeeDTOIn.getName());
        myUser.setEmail(employeeDTOIn.getEmail());
        myUser = authRepository.save(myUser);

        Employee employee = myUser.getEmployee();
        employee.setPhoneNumber(employeeDTOIn.getPhoneNumber());
        employeeRepository.save(employee);

    }

    public EmployeeDTOOut convertEmployeeDTO(Employee employee){
        return new EmployeeDTOOut(employee.getMyUser().getUsername(),employee.getMyUser().getName(),employee.getMyUser().getEmail(),employee.getPhoneNumber());
    }
}
