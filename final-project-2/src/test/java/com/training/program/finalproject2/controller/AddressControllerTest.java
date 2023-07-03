package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.AddressDto;
import com.training.program.finalproject2.entity.Address;
import com.training.program.finalproject2.service.interfaces.AddressService;
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
import static org.mockito.Mockito.*;

class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;
    private Address address;
    private AddressDto addressDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.address = Address.builder()
                .country("Colombia")
                .state("Antioquia")
                .city("Medellin")
                .street("Calle 2")
                .id(1).build();

        this.addressDto = AddressDto.builder()
                .country("Colombia")
                .state("Antioquia")
                .city("Medellin")
                .street("Calle 2").build();
    }

    @Test
    void getAddressById() {
        when(addressService.getAddressById(1)).thenReturn(addressDto);

        ResponseEntity<AddressDto> response = addressController.getAddressById(1);

        verify(addressService).getAddressById(1);
        assertAll(
                () -> assertThat(response.getStatusCode(), is(HttpStatus.OK))
        );
    }

    @Test
    void getAllAddresses() {
        List<AddressDto> list = new ArrayList<>();
        list.add(addressDto);
        when(addressService.getAllAddresses()).thenReturn(list);

        ResponseEntity<List<AddressDto>> response = addressController.getAllAddresses();

        verify(addressService).getAllAddresses();
        assertThat(response.getBody(), hasSize(1));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void createAddress() {

        ResponseEntity<HttpStatus> response = addressController.createAddress(addressDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void updateAddress() {

        ResponseEntity<HttpStatus> response = addressController.createAddress(addressDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

    }

    @Test
    void deleteAddress() {

        ResponseEntity<HttpStatus> response = addressController.deleteAddress(1);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}