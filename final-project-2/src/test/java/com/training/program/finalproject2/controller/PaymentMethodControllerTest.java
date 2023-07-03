package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.PaymentMethodDto;
import com.training.program.finalproject2.service.interfaces.PaymentMethodService;
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

class PaymentMethodControllerTest {

    @Mock
    private PaymentMethodService paymentMethodService;

    @InjectMocks
    private PaymentMethodController paymentMethodController;

    private PaymentMethodDto paymentMethodDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.paymentMethodDto = PaymentMethodDto.builder()
                .type("Credit")
                .build();
    }

    @Test
    void getPaymentMethodById() {
        when(paymentMethodService.getPaymentMethodDtoById(1)).thenReturn(paymentMethodDto);

        ResponseEntity<PaymentMethodDto> response = paymentMethodController.getPaymentMethodById(1);

        verify(paymentMethodService).getPaymentMethodDtoById(1);
        assertAll(
                () -> assertThat(response.getStatusCode(), is(HttpStatus.OK))
        );
    }

    @Test
    void getAllPaymentMethodes() {
        List<PaymentMethodDto> list = new ArrayList<>();
        list.add(paymentMethodDto);
        when(paymentMethodService.getAllPaymentMethods()).thenReturn(list);

        ResponseEntity<List<PaymentMethodDto>> response = paymentMethodController.getAllPaymentMethods();

        verify(paymentMethodService).getAllPaymentMethods();
        assertThat(response.getBody(), hasSize(1));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void createPaymentMethod() {

        ResponseEntity<HttpStatus> response = paymentMethodController.createPaymentMethod(paymentMethodDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void updatePaymentMethod() {

        ResponseEntity<HttpStatus> response = paymentMethodController.createPaymentMethod(paymentMethodDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

    }

    @Test
    void deletePaymentMethod() {

        ResponseEntity<HttpStatus> response = paymentMethodController.deletePaymentMethod(1);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}