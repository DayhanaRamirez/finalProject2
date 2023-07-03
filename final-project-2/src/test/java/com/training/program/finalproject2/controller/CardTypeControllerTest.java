package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.AddressDto;
import com.training.program.finalproject2.dto.CardTypeDto;
import com.training.program.finalproject2.entity.Address;
import com.training.program.finalproject2.entity.CardType;
import com.training.program.finalproject2.service.CardTypeServiceImpl;
import com.training.program.finalproject2.service.interfaces.CardTypeService;
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

class CardTypeControllerTest {

    @Mock
    private CardTypeService cardTypeService;

    @InjectMocks
    private CardTypeController cardTypeController;

    private CardTypeDto cardTypeDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.cardTypeDto = CardTypeDto.builder()
                .type("Credit")
                .build();

    }

    @Test
    void getCardTypeById() {
        when(cardTypeService.getCardTypeById(1)).thenReturn(cardTypeDto);

        ResponseEntity<CardTypeDto> response = cardTypeController.getCardTypById(1);

        verify(cardTypeService).getCardTypeById(1);
        assertAll(
                () -> assertThat(response.getStatusCode(), is(HttpStatus.OK))
        );
    }

    @Test
    void getAllCardTypees() {
        List<CardTypeDto> list = new ArrayList<>();
        list.add(cardTypeDto);
        when(cardTypeService.getAllCardTypes()).thenReturn(list);

        ResponseEntity<List<CardTypeDto>> response = cardTypeController.getAllCardTypes();

        verify(cardTypeService).getAllCardTypes();
        assertThat(response.getBody(), hasSize(1));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void createCardType() {

        ResponseEntity<HttpStatus> response = cardTypeController.createCardType(cardTypeDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void updateCardType() {

        ResponseEntity<HttpStatus> response = cardTypeController.createCardType(cardTypeDto);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

    }

    @Test
    void deleteCardType() {

        ResponseEntity<HttpStatus> response = cardTypeController.deleteCardType(1);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}