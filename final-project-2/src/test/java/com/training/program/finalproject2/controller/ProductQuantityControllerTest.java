package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.ProductQuantityDto;
import com.training.program.finalproject2.service.interfaces.ProductQuantityService;
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

class ProductQuantityControllerTest {

    @Mock
    private ProductQuantityService productQuantityService;

    @InjectMocks
    private ProductQuantityController productQuantityController;

    private ProductQuantityDto productQuantityDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.productQuantityDto = ProductQuantityDto.builder()
                .idProduct(1)
                .idCheckout(1)
                .quantity(5)
                .build();
    }

    @Test
    void getProductQuantityById() {
        when(productQuantityService.getProductQuantityById(1)).thenReturn(productQuantityDto);

        ResponseEntity<ProductQuantityDto> response = productQuantityController.getProductQuantityById(1);

        verify(productQuantityService).getProductQuantityById(1);
        assertAll(
                () -> assertThat(response.getStatusCode(), is(HttpStatus.OK))
        );
    }

    @Test
    void getAllProductQuantityes() {
        List<ProductQuantityDto> list = new ArrayList<>();
        list.add(productQuantityDto);
        when(productQuantityService.getAllProductQuantity()).thenReturn(list);

        ResponseEntity<List<ProductQuantityDto>> response = productQuantityController.getALlProductQuantity();

        verify(productQuantityService).getAllProductQuantity();
        assertThat(response.getBody(), hasSize(1));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void createProductQuantity() {

        ResponseEntity<HttpStatus> response = productQuantityController.createProductQuantity(productQuantityDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void updateProductQuantity() {

        ResponseEntity<HttpStatus> response = productQuantityController.createProductQuantity(productQuantityDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

    }

    @Test
    void deleteProductQuantity() {

        ResponseEntity<HttpStatus> response = productQuantityController.deleteProductQuantity(1);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void modifyProductQuantity() {

        ResponseEntity<HttpStatus> response = productQuantityController.modifyProductQuantity(productQuantityDto);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}