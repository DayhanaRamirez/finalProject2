package com.training.program.finalproject2.service.interfaces;

import com.training.program.finalproject2.dto.CustomerDto;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.exception.CreateUserEmailException;
import com.training.program.finalproject2.exception.NotFoundException;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(CustomerDto customerDto) throws CreateUserEmailException;

    CustomerDto getCustomerById(int id) throws NotFoundException;

    List<CustomerDto> getAllCustomers();

    Customer updateCustomer(CustomerDto customerDto, int id) throws CreateUserEmailException;

    void deleteCustomerById(int id) throws NotFoundException;
}
