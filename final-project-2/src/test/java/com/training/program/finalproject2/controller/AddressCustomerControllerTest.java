package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.AddressCustomerDto;
import com.training.program.finalproject2.dto.AddressDto;
import com.training.program.finalproject2.entity.Address;
import com.training.program.finalproject2.service.interfaces.AddressCustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddressCustomerControllerTest {

    @Mock
    private AddressCustomerService addressCustomerService;

    @InjectMocks
    private AddressCustomerController addressCustomerController;
    private AddressCustomerDto addressCustomerDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.addressCustomerDto = addressCustomerDto.builder()
                .idCustomer(1)
                .idAddress(1)
                .active(false).build();
    }

    @Test
    void getAddressCustomerById() {
        when(addressCustomerService.getAddressCustomerById(1)).thenReturn(addressCustomerDto);

        ResponseEntity<AddressCustomerDto> response = addressCustomerController.getAddressCustomerById(1);

        verify(addressCustomerService).getAddressCustomerById(1);
        assertAll(
                () -> assertThat(response.getStatusCode(), is(HttpStatus.OK))
        );
    }

    @Test
    void getAllAddressCustomeres() {
        List<AddressCustomerDto> list = new ArrayList<>();
        list.add(addressCustomerDto);
        when(addressCustomerService.getAllAddressCustomer()).thenReturn(list);

        ResponseEntity<List<AddressCustomerDto>> response = addressCustomerController.getAllAddressCustomer();

        verify(addressCustomerService).getAllAddressCustomer();
        assertThat(response.getBody(), hasSize(1));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void createAddressCustomer() {

        ResponseEntity<HttpStatus> response = addressCustomerController.createAddressCustomer(addressCustomerDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void updateAddressCustomer() {
        ResponseEntity<HttpStatus> response = addressCustomerController.createAddressCustomer(addressCustomerDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

    }

    @Test
    void deleteAddressCustomer() {
        ResponseEntity<HttpStatus> response = addressCustomerController.deleteAddressCustomer(1);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void setActiveAddressCustomer() {
        ResponseEntity<HttpStatus> response = addressCustomerController.setActiveAddressCustomer(1);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}