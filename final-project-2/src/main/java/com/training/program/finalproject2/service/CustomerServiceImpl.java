package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.CustomerDto;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.exception.CreateUserEmailException;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.mapper.CustomerMapper;
import com.training.program.finalproject2.repository.CustomerRepository;
import com.training.program.finalproject2.service.interfaces.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDto getCustomerById(int id) throws NotFoundException {
        try {
            Customer customer = customerRepository.getReferenceById(id);
            return customerMapper.customerEntityToCustomerDto(customer);
        }catch(EntityNotFoundException e){
            throw new EntityNotFoundException("Couldn't find a customer with the given id");
        }
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for(Customer customer : customers){
            customerDtoList.add(customerMapper.customerEntityToCustomerDto(customer));
        }
        return customerDtoList;
    }

    @Override
    public Customer createCustomer(CustomerDto customerDto) throws CreateUserEmailException {
        if (customerRepository.findCustomerByEmail(customerDto.getEmail()) != null) {
            throw new CreateUserEmailException("A customer already exists with the given email");
        }
        return customerRepository.save(customerMapper.customerDtoToCustomerEntity(customerDto));
    }

    @Override
    public Customer updateCustomer(CustomerDto customerDto, int id) throws CreateUserEmailException {
        try {
            Customer customer = customerMapper.customerDtoToCustomerEntity(customerDto);
            if(customerRepository.findCustomerByEmail(customerDto.getEmail()) != null){
                throw new CreateUserEmailException("A customer already exists with the given email");
            }

            customer.setId(id);
            customer.setFirstName(customerDto.getFirstName());
            customer.setLastName(customerDto.getLastName());
            return customerRepository.save(customer);

        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException("Couldn't find a customer with the given id");
        }
    }

    @Override
    public void deleteCustomerById(int id) throws NotFoundException {
        try{
            customerRepository.deleteById(id);
        }catch (Exception e){
            throw new NotFoundException("Couldn't find a customer with the given id");
        }
    }
}
