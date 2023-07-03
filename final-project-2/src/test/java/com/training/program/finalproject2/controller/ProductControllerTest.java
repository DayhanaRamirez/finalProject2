package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.ProductDto;
import com.training.program.finalproject2.service.interfaces.ProductService;
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

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDto productDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.productDto = ProductDto.builder()
                .name("Chocolate")
                .description("Barra 8x4")
                .price(10000D)
                .build();
    }

    @Test
    void getProductById() {
        when(productService.getProductById(1)).thenReturn(productDto);

        ResponseEntity<ProductDto> response = productController.getProductById(1);

        verify(productService).getProductById(1);
        assertAll(
                () -> assertThat(response.getStatusCode(), is(HttpStatus.OK))
        );
    }

    @Test
    void getAllProductes() {
        List<ProductDto> list = new ArrayList<>();
        list.add(productDto);
        when(productService.getAllProducts()).thenReturn(list);

        ResponseEntity<List<ProductDto>> response = productController.getAllProducts();

        verify(productService).getAllProducts();
        assertThat(response.getBody(), hasSize(1));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void createProduct() {

        ResponseEntity<HttpStatus> response = productController.createProduct(productDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void updateProduct() {

        ResponseEntity<HttpStatus> response = productController.createProduct(productDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

    }

    @Test
    void deleteProduct() {

        ResponseEntity<HttpStatus> response = productController.deleteProduct(1);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}