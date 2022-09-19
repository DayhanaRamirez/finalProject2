package com.training.program.finalproject2.mapper;

import com.training.program.finalproject2.dto.UserPaymentMethodDto;
import com.training.program.finalproject2.entity.CardType;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.entity.PaymentMethod;
import com.training.program.finalproject2.entity.UserPaymentMethod;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserPaymentMethodMapper {
    public UserPaymentMethod userPaymentMethodDtoToEntity(UserPaymentMethodDto userPaymentMethodDto, CardType cardType, Customer customer, PaymentMethod paymentMethod){
        return UserPaymentMethod.builder()
                .cardNumber(userPaymentMethodDto.getCardNumber())
                .active(userPaymentMethodDto.isActive()).
                cardType(cardType)
                .customer(customer)
                .paymentMethod(paymentMethod)
                .build();
    }

    public UserPaymentMethodDto userPaymentMethodEntityToDto(UserPaymentMethod userPaymentMethod){
        return UserPaymentMethodDto.builder()
                .cardNumber(userPaymentMethod.getCardNumber())
                .isActive(userPaymentMethod.isActive())
                .idCardType(userPaymentMethod.getCardType().getId())
                .idCustomer(userPaymentMethod.getCustomer().getId())
                .idPaymentMethod(userPaymentMethod.getPaymentMethod().getId())
                .build();
    }
}
