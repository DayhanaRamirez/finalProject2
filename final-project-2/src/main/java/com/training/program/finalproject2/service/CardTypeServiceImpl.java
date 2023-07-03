package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.AddressDto;
import com.training.program.finalproject2.dto.CardTypeDto;
import com.training.program.finalproject2.dto.CustomerDto;
import com.training.program.finalproject2.entity.CardType;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.exception.CardTypeAlreadyExistException;
import com.training.program.finalproject2.exception.CreateUserEmailException;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.mapper.CardTypeMapper;
import com.training.program.finalproject2.repository.CardTypeRepository;
import com.training.program.finalproject2.service.interfaces.CardTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CardTypeServiceImpl implements CardTypeService {

    private final CardTypeRepository cardTypeRepository;
    private final CardTypeMapper cardTypeMapper;

    @Override
    public CardTypeDto getCardTypeById(int id) throws NotFoundException {
        try {
            CardType cardType = cardTypeRepository.getReferenceById(id);
            return cardTypeMapper.cardTypeEntityToCardTypeDto(cardType);
        }catch(EntityNotFoundException e){
            throw new NotFoundException("Couldn't find a card type with the given id");
        }
    }

    @Override
    public List<CardTypeDto> getAllCardTypes() {
        List<CardType> cardTypes = cardTypeRepository.findAll();
        List<CardTypeDto> cardTypeDtoList = new ArrayList<>();
        for(CardType cardType : cardTypes){
            cardTypeDtoList.add(cardTypeMapper.cardTypeEntityToCardTypeDto(cardType));
        }
        return cardTypeDtoList;
    }

    @Override
    public CardType createCardType(CardTypeDto cardTypeDto) throws CardTypeAlreadyExistException {
        if (cardTypeRepository.findCardTypeByType(cardTypeDto.getType()) != null) {
            throw new CardTypeAlreadyExistException("A card type already exists with the given type");
        }
        return cardTypeRepository.save(cardTypeMapper.cardTypeDtoToCardTypeEntity(cardTypeDto));
    }

    @Override
    public CardType updateCardType(CardTypeDto cardTypeDto, int id) throws CardTypeAlreadyExistException {
        try {
            CardType cardType = cardTypeRepository.getReferenceById(id);
            if(cardTypeRepository.findCardTypeByType(cardTypeDto.getType()) != null){
                throw new CardTypeAlreadyExistException("A card type already exists with the given type");
            }
            cardType.setType(cardTypeDto.getType());
            return cardTypeRepository.save(cardType);

        }catch (EntityNotFoundException e){
            throw new NotFoundException("Couldn't find a card type with the given id");
        }
    }

    @Override
    public void deleteCardTypeId(int id) throws NotFoundException {
        try {
            cardTypeRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException("Couldn't find a card type with the given id");
        }

    }
}
