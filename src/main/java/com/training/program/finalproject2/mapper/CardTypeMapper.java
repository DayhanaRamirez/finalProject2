package com.training.program.finalproject2.mapper;

import com.training.program.finalproject2.dto.CardTypeDto;
import com.training.program.finalproject2.entity.CardType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class CardTypeMapper {
    public CardType cardTypeDtoToCardTypeEntity(CardTypeDto cardTypeDto){
        return CardType.builder()
                .type(cardTypeDto.getType())
                .build();
    }

    public CardTypeDto cardTypeEntityToCardTypeDto(CardType cardType){
        return CardTypeDto.builder()
                .type(cardType.getType())
                .build();
    }

}
