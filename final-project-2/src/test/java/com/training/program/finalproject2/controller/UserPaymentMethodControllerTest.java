package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.UserPaymentMethodDto;
import com.training.program.finalproject2.service.interfaces.UserPaymentMethodService;
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

class UserPaymentMethodControllerTest {
    @Mock
    private UserPaymentMethodService userPaymentMethodService;

    @InjectMocks
    private UserPaymentMethodController userPaymentMethodController;

    private UserPaymentMethodDto userPaymentMethodDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.userPaymentMethodDto = UserPaymentMethodDto.builder()
                .idPaymentMethod(1)
                .idCustomer(1)
                .idCardType(1)
                .build();
    }

    @Test
    void getUserPaymentMethodById() {
        when(userPaymentMethodService.getUserPaymentMethodById(1)).thenReturn(userPaymentMethodDto);

        ResponseEntity<UserPaymentMethodDto> response = userPaymentMethodController.getUserPaymentMethodById(1);

        verify(userPaymentMethodService).getUserPaymentMethodById(1);
        assertAll(
                () -> assertThat(response.getStatusCode(), is(HttpStatus.OK))
        );
    }

    @Test
    void getAllUserPaymentMethodes() {
        List<UserPaymentMethodDto> list = new ArrayList<>();
        list.add(userPaymentMethodDto);
        when(userPaymentMethodService.getAllUserPaymentMethod()).thenReturn(list);

        ResponseEntity<List<UserPaymentMethodDto>> response = userPaymentMethodController.getAllUserPaymentMethod();

        verify(userPaymentMethodService).getAllUserPaymentMethod();
        assertThat(response.getBody(), hasSize(1));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void createUserPaymentMethod() {

        ResponseEntity<HttpStatus> response = userPaymentMethodController.createUserPaymentMethod(userPaymentMethodDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void updateUserPaymentMethod() {

        ResponseEntity<HttpStatus> response = userPaymentMethodController.createUserPaymentMethod(userPaymentMethodDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

    }

    @Test
    void deleteUserPaymentMethod() {

        ResponseEntity<HttpStatus> response = userPaymentMethodController.deleteUserPaymentMethod(1);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void modifyUserPaymentMethod() {

        ResponseEntity<HttpStatus> response = userPaymentMethodController.setActiveUserPaymentMethod(1);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}