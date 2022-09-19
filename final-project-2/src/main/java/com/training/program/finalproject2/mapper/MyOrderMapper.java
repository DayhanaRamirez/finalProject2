package com.training.program.finalproject2.mapper;

import com.training.program.finalproject2.dto.MyOrderDto;
import com.training.program.finalproject2.entity.Checkout;
import com.training.program.finalproject2.entity.MyOrder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class MyOrderMapper {
    public MyOrder myOrderDtoToEntity(MyOrderDto myOrderDto, Checkout checkout){
        return MyOrder.builder()
                .date(myOrderDto.getDate())
                .checkout(checkout)
                .build();
    }

    public MyOrderDto myOrderEntityToDto(MyOrder myOrder){
        return MyOrderDto.builder()
                .date(myOrder.getDate())
                .idCheckout(myOrder.getCheckout().getId())
                .build();
    }
}
