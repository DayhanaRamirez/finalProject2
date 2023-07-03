package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.MyOrderDto;
import com.training.program.finalproject2.service.interfaces.MyOrderService;
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

class MyOrderControllerTest {

    @Mock
    private MyOrderService myOrderService;

    @InjectMocks
    private MyOrderController myOrderController;

    private MyOrderDto myOrderDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.myOrderDto = MyOrderDto.builder()
                .date("10-10-2020")
                .build();
    }

    @Test
    void getMyOrderById() {
        when(myOrderService.getMyOrderById(1)).thenReturn(myOrderDto);

        ResponseEntity<MyOrderDto> response = myOrderController.getMyOrderById(1);

        verify(myOrderService).getMyOrderById(1);
        assertAll(
                () -> assertThat(response.getStatusCode(), is(HttpStatus.OK))
        );
    }

    @Test
    void getAllMyOrderes() {
        List<MyOrderDto> list = new ArrayList<>();
        list.add(myOrderDto);
        when(myOrderService.getAllMyOrder()).thenReturn(list);

        ResponseEntity<List<MyOrderDto>> response = myOrderController.getAllMyOrder();

        verify(myOrderService).getAllMyOrder();
        assertThat(response.getBody(), hasSize(1));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void createMyOrder() {

        ResponseEntity<HttpStatus> response = myOrderController.createMyOrder(myOrderDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void updateMyOrder() {

        ResponseEntity<HttpStatus> response = myOrderController.createMyOrder(myOrderDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

    }

    @Test
    void deleteMyOrder() {

        ResponseEntity<HttpStatus> response = myOrderController.deleteMyOrderById(1);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}