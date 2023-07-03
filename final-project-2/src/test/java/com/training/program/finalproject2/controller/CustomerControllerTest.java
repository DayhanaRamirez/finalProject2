package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.CustomerDto;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.service.interfaces.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private CustomerDto customerDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.customerDto = CustomerDto.builder()
                .firstName("Dayhana")
                .lastName("Ramirez")
                .email("dayha@gmail.com")
                .build();
    }

    @Test
    void getCustomerById() {
        when(customerService.getCustomerById(1)).thenReturn(customerDto);

        ResponseEntity<CustomerDto> response = customerController.getCustomerById(1);

        verify(customerService).getCustomerById(1);
        assertAll(
                () -> assertThat(response.getStatusCode(), is(HttpStatus.OK))
        );
    }

    @Test
    void getAllCustomeres() {
        List<CustomerDto> list = new ArrayList<>();
        list.add(customerDto);
        when(customerService.getAllCustomers()).thenReturn(list);

        ResponseEntity<List<CustomerDto>> response = customerController.getAllCustomers();

        verify(customerService).getAllCustomers();
        assertThat(response.getBody(), hasSize(1));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void createCustomer() {

        ResponseEntity<HttpStatus> response = customerController.createCustomer(customerDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void updateCustomer() {

        ResponseEntity<HttpStatus> response = customerController.createCustomer(customerDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

    }

    @Test
    void deleteCustomer() {

        ResponseEntity<HttpStatus> response = customerController.deleteCustomer(1);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}