package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.CustomerDto;
import com.training.program.finalproject2.dto.CustomerDto;
import com.training.program.finalproject2.dto.CustomerDto;
import com.training.program.finalproject2.dto.CustomerDto;
import com.training.program.finalproject2.entity.*;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.exception.AddressAlreadyExistsException;
import com.training.program.finalproject2.exception.CreateUserEmailException;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.mapper.CustomerMapper;
import com.training.program.finalproject2.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;

    private CustomerDto customerDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.customer = Customer.builder()
                .email("dayha@gmail.com")
                .lastName("Ramirez")
                .firstName("Dayhana")
                .id(1).build();

        this.customerDto = CustomerDto.builder()
                .email("dayha@gmail.com")
                .lastName("Ramirez")
                .firstName("Dayhana").build();
    }

    @Test
    void getCustomerByIdIsSuccessful() {

        //Arrange
        when(customerRepository.getReferenceById(anyInt())).thenReturn(customer);
        when(customerMapper.customerEntityToCustomerDto(any(Customer.class))).thenReturn(customerDto);

        //Act
        CustomerDto response = customerService.getCustomerById(1);

        //Assert
        verify(customerRepository).getReferenceById(anyInt());
        verify(customerMapper).customerEntityToCustomerDto(any(Customer.class));
    }

    @Test
    void getCustomerByIdDoesNotExist(){

        //Arrange
        when(customerRepository.getReferenceById(anyInt())).thenThrow(EntityNotFoundException.class);

        //Act
        final EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            customerService.getCustomerById(anyInt());
        });

        //Assert
        verify(customerRepository).getReferenceById(anyInt());
        verify(customerMapper, times(0)).customerEntityToCustomerDto(any(Customer.class));
        assertAll(
                () -> assertThat(exception.getClass(), is(EntityNotFoundException.class)),
                () -> assertThat(exception.getMessage(), is("Couldn't find a customer with the given id"))
        );
    }

    @Test
    void getAllCustomers() {

        //Arrange
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);


        when(customerRepository.findAll()).thenReturn(customers);
        when(customerMapper.customerEntityToCustomerDto(customers.get(0))).thenReturn(customerDto);

        //Act
        List<CustomerDto> response = customerService.getAllCustomers();

        //Assert
        verify(customerRepository).findAll();
        verify(customerMapper).customerEntityToCustomerDto(any(Customer.class));
        assertAll(
                () -> assertThat(response.size(), is(1))
        );
    }
    @Test
    void createCustomerIsSuccessful() {

        //Act
        when(customerMapper.customerDtoToCustomerEntity(any(CustomerDto.class))).thenReturn(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(any(Customer.class));
        when(customerRepository.findCustomerByEmail(
                customer.getEmail()))
                .thenReturn(any(Customer.class));

        //Act
        Customer response = customerService.createCustomer(customerDto);

        //Arrange
        verify(customerRepository).save(any(Customer.class));
        verify(customerMapper).customerDtoToCustomerEntity(any(CustomerDto.class));
        verify(customerRepository).findCustomerByEmail(
                eq(customer.getEmail()));
    }

    @Test
    void createCustomerAlreadyExists() {

        //Act
       when(customerRepository.findCustomerByEmail(
                eq(customerDto.getEmail())))
                .thenReturn(customer);

        //Act
        final CreateUserEmailException exception = Assertions.assertThrows(CreateUserEmailException.class, () -> {
            customerService.createCustomer(customerDto);
        });
        //Arrange
        verify(customerRepository).findCustomerByEmail(
                anyString());
        assertAll(
                () -> assertThat(exception.getClass(), is(CreateUserEmailException.class)),
                () -> assertThat(exception.getMessage(), is("A customer already exists with the given email"))
        );
    }

    @Test
    void updateCustomerIsSuccessful() {

        //Arrange
        customerDto.setFirstName("Daniel");
        customerDto.setLastName("Cardona");
        when(customerMapper.customerDtoToCustomerEntity(any(CustomerDto.class))).thenReturn(customer);
        when(customerRepository.findCustomerByEmail(
                eq(customerDto.getEmail())))
                .thenReturn(null);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        //Act
        Customer response = customerService.updateCustomer(customerDto, 1);

        //Assert
        verify(customerRepository).findCustomerByEmail(
                eq(customer.getEmail()));
        verify(customerRepository).save(
                any(Customer.class));
        assertAll(
                () -> assertThat(response.getFirstName(), is("Daniel")),
                () -> assertThat(response.getLastName(), is("Cardona"))
        );
    }

    @Test
    void deleteCustomerById() {
        //Arrange
        doNothing().when(customerRepository).deleteById(anyInt());

        //Act
        customerService.deleteCustomerById(1);

        //Assert
        verify(customerRepository).deleteById(anyInt());
    }

    @Test
    void deleteAddressByIdIsNotSuccessful() {
        //Arrange
        doThrow(NotFoundException.class).when(customerRepository).deleteById(anyInt());

        //Act
        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            customerService.deleteCustomerById(1);
        });

        //Assert
        verify(customerRepository).deleteById(anyInt());
        assertAll(
                () -> assertThat(exception.getClass(), is(NotFoundException.class)),
                () -> assertThat(exception.getMessage(), is("Couldn't find a customer with the given id"))
        );
    }
}