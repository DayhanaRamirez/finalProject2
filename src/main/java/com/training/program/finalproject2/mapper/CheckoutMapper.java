package com.training.program.finalproject2.mapper;

import com.training.program.finalproject2.dto.CheckoutDto;
import com.training.program.finalproject2.entity.Checkout;
import com.training.program.finalproject2.entity.Customer;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class CheckoutMapper {

    public Checkout checkoutDtoToCheckoutEntity(CheckoutDto checkoutDto, Customer customer) {
        return Checkout.builder()
                .date(checkoutDto.getDate())
                .customer(customer)
                .build();
    }

    public CheckoutDto checkoutEntityToCheckoutDto(Checkout checkout) {
        return CheckoutDto.builder()
                .date(checkout.getDate()).
                idCustomer(checkout.getCustomer().getId())
                .build();
    }
}
