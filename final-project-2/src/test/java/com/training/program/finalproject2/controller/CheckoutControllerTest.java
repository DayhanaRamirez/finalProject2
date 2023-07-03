package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.CheckoutDto;
import com.training.program.finalproject2.dto.CheckoutDto;
import com.training.program.finalproject2.entity.Checkout;
import com.training.program.finalproject2.service.CheckoutServiceImpl;
import com.training.program.finalproject2.service.interfaces.CheckoutService;
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

class CheckoutControllerTest {

    @Mock
    private CheckoutService checkoutService;

    @InjectMocks
    private CheckoutController checkoutController;
    private CheckoutDto checkoutDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.checkoutDto = CheckoutDto.builder()
                .idCustomer(1)
                .date("10-10-2020")
                .build();
    }

    @Test
    void getCheckoutById() {
        when(checkoutService.getCheckoutById(1)).thenReturn(checkoutDto);

        ResponseEntity<CheckoutDto> response = checkoutController.getCheckoutId(1);

        verify(checkoutService).getCheckoutById(1);
        assertAll(
                () -> assertThat(response.getStatusCode(), is(HttpStatus.OK))
        );
    }

    @Test
    void getAllCheckoutes() {
        List<CheckoutDto> list = new ArrayList<>();
        list.add(checkoutDto);
        when(checkoutService.getAllCheckouts()).thenReturn(list);

        ResponseEntity<List<CheckoutDto>> response = checkoutController.getAllCheckouts();

        verify(checkoutService).getAllCheckouts();
        assertThat(response.getBody(), hasSize(1));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void createCheckout() {

        ResponseEntity<HttpStatus> response = checkoutController.createCheckout(checkoutDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void updateCheckout() {

        ResponseEntity<HttpStatus> response = checkoutController.createCheckout(checkoutDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

    }

    @Test
    void deleteCheckout() {

        ResponseEntity<HttpStatus> response = checkoutController.deleteCheckout(1);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}