package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.CheckoutDto;
import com.training.program.finalproject2.dto.ProductQuantityDto;
import com.training.program.finalproject2.entity.Checkout;
import com.training.program.finalproject2.request.CheckOutRequest;
import com.training.program.finalproject2.service.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class LogicServiceImpl implements LogicService {

    private final CheckoutService checkoutService;
    private final CustomerService customerService;
    private final ProductQuantityService productQuantityService;
    private final ProductService productService;

    public void startCheckout(CheckOutRequest checkOutRequest){
        CheckoutDto checkoutDto = CheckoutDto.builder().date(LocalDate.now().toString()).idCustomer(checkOutRequest.getIdCustomer()).build();
        Checkout checkout = checkoutService.createCheckout(checkoutDto);
        ProductQuantityDto productQuantityDto = ProductQuantityDto.builder()
                .quantity(checkOutRequest.getQuantity())
                .idCheckout(checkout.getId())
                .idProduct(checkOutRequest.getIdProduct())
                .build();
        productQuantityService.createProductQuantity(productQuantityDto);
    }
}
