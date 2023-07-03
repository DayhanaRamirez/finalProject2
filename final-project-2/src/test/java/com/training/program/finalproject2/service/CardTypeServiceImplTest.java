package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.CardTypeDto;
import com.training.program.finalproject2.dto.CardTypeDto;
import com.training.program.finalproject2.entity.CardType;
import com.training.program.finalproject2.entity.CardType;
import com.training.program.finalproject2.exception.CardTypeAlreadyExistException;
import com.training.program.finalproject2.exception.CreateUserEmailException;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.mapper.CardTypeMapper;
import com.training.program.finalproject2.repository.CardTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class CardTypeServiceImplTest {

    @Mock
    private CardTypeRepository cardTypeRepository;
    @Mock
    private CardTypeMapper cardTypeMapper;

    @InjectMocks
    private CardTypeServiceImpl cardTypeService;

    private CardType cardType;

    private CardTypeDto cardTypeDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.cardType = CardType.builder()
                .type("Debit")
                .id(1).build();

        this.cardTypeDto = CardTypeDto.builder()
                .type("Debit").build();
    }

    @Test
    void getCardTypeByIdIsSuccessful() {

        //Arrange
        when(cardTypeRepository.getReferenceById(anyInt())).thenReturn(cardType);
        when(cardTypeMapper.cardTypeEntityToCardTypeDto(any(CardType.class))).thenReturn(cardTypeDto);

        //Act
        CardTypeDto response = cardTypeService.getCardTypeById(1);

        //Assert
        verify(cardTypeRepository).getReferenceById(anyInt());
        verify(cardTypeMapper).cardTypeEntityToCardTypeDto(any(CardType.class));
    }

    @Test
    void getCardTypeByIdDoesNotExist(){

        //Arrange
        when(cardTypeRepository.getReferenceById(anyInt())).thenThrow(EntityNotFoundException.class);

        //Act
        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            cardTypeService.getCardTypeById(anyInt());
        });

        //Assert
        verify(cardTypeRepository).getReferenceById(anyInt());
        verify(cardTypeMapper, times(0)).cardTypeEntityToCardTypeDto(any(CardType.class));
        assertAll(
                () -> assertThat(exception.getClass(), is(NotFoundException.class)),
                () -> assertThat(exception.getMessage(), is("Couldn't find a card type with the given id"))
        );
    }

    @Test
    void getAllCardTypes() {

        //Arrange
        List<CardType> cardTypes = new ArrayList<>();
        cardTypes.add(cardType);


        when(cardTypeRepository.findAll()).thenReturn(cardTypes);
        when(cardTypeMapper.cardTypeEntityToCardTypeDto(cardTypes.get(0))).thenReturn(cardTypeDto);

        //Act
        List<CardTypeDto> response = cardTypeService.getAllCardTypes();

        //Assert
        verify(cardTypeRepository).findAll();
        verify(cardTypeMapper).cardTypeEntityToCardTypeDto(any(CardType.class));
        assertAll(
                () -> assertThat(response.size(), is(1))
        );
    }
    @Test
    void createCardTypeIsSuccessful() {

        //Act
        when(cardTypeMapper.cardTypeDtoToCardTypeEntity(any(CardTypeDto.class))).thenReturn(cardType);
        when(cardTypeRepository.save(any(CardType.class))).thenReturn(any(CardType.class));
        when(cardTypeRepository.findCardTypeByType(
                cardType.getType()))
                .thenReturn(any(CardType.class));

        //Act
        CardType response = cardTypeService.createCardType(cardTypeDto);

        //Arrange
        verify(cardTypeRepository).save(any(CardType.class));
        verify(cardTypeMapper).cardTypeDtoToCardTypeEntity(any(CardTypeDto.class));
        verify(cardTypeRepository).findCardTypeByType(
                eq(cardType.getType()));
    }

    @Test
    void createCardTypeAlreadyExists() {

        //Act
        when(cardTypeRepository.findCardTypeByType(
                eq(cardTypeDto.getType())))
                .thenReturn(cardType);

        //Act
        final CardTypeAlreadyExistException exception = Assertions.assertThrows(CardTypeAlreadyExistException.class, () -> {
            cardTypeService.createCardType(cardTypeDto);
        });
        //Arrange
        verify(cardTypeRepository).findCardTypeByType(
                anyString());
        assertAll(
                () -> assertThat(exception.getClass(), is(CardTypeAlreadyExistException.class)),
                () -> assertThat(exception.getMessage(), is("A card type already exists with the given type"))
        );
    }

    @Test
    void updateCardTypeIsSuccessful() {

        //Arrange
        cardTypeDto.setType("Credit");
        when(cardTypeRepository.getReferenceById(1)).thenReturn(cardType);
        when(cardTypeRepository.findCardTypeByType(
                eq(cardTypeDto.getType())))
                .thenReturn(null);
        when(cardTypeRepository.save(any(CardType.class))).thenReturn(cardType);

        //Act
        CardType response = cardTypeService.updateCardType(cardTypeDto, 1);

        //Assert
        verify(cardTypeRepository).findCardTypeByType(
                eq(cardType.getType()));
        verify(cardTypeRepository).save(
                any(CardType.class));
        assertAll(
                () -> assertThat(response.getType(), is("Credit"))
        );
    }

    @Test
    void deleteCardTypeById() {
        //Arrange
        doNothing().when(cardTypeRepository).deleteById(anyInt());

        //Act
        cardTypeService.deleteCardTypeId(1);

        //Assert
        verify(cardTypeRepository).deleteById(anyInt());
    }

    @Test
    void deleteAddressByIdIsNotSuccessful() {
        //Arrange
        doThrow(NotFoundException.class).when(cardTypeRepository).deleteById(anyInt());

        //Act
        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            cardTypeService.deleteCardTypeId(1);
        });

        //Assert
        verify(cardTypeRepository).deleteById(anyInt());
        assertAll(
                () -> assertThat(exception.getClass(), is(NotFoundException.class)),
                () -> assertThat(exception.getMessage(), is("Couldn't find a card type with the given id"))
        );
    }
}