package com.training.program.finalproject2.mapper;

import com.training.program.finalproject2.dto.PaymentMethodDto;
import com.training.program.finalproject2.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PaymentMethodMapper {

    public PaymentMethod paymentMethodDtoToPaymentMethodEntity(PaymentMethodDto paymentMethodDto){
        return PaymentMethod.builder()
                .type(paymentMethodDto.getType())
                .build();
    }

    public PaymentMethodDto paymentMethodEntityToPaymentMethodDto(PaymentMethod paymentMethod){
        return PaymentMethodDto.builder()
                .type(paymentMethod.getType())
                .build();
    }

}
