package com.training.program.finalproject2.service.interfaces;

import com.training.program.finalproject2.dto.CardTypeDto;
import com.training.program.finalproject2.entity.CardType;
import com.training.program.finalproject2.exception.CardTypeAlreadyExistException;
import com.training.program.finalproject2.exception.NotFoundException;

import java.util.List;

public interface CardTypeService {

    CardType createCardType(CardTypeDto cardTypeDto) throws CardTypeAlreadyExistException;

    CardTypeDto getCardTypeById(int id) throws NotFoundException;

    List<CardTypeDto> getAllCardTypes();

    CardType updateCardType(CardTypeDto cardTypeDto, int id) throws CardTypeAlreadyExistException;

    void deleteCardTypeId(int id) throws NotFoundException;

}
