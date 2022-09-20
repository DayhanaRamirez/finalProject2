package com.training.program.finalproject2.service.interfaces;

import com.training.program.finalproject2.dto.CheckoutDto;
import com.training.program.finalproject2.entity.Checkout;
import com.training.program.finalproject2.exception.NotFoundException;

import java.util.List;

public interface CheckoutService {
    Checkout createCheckout(CheckoutDto checkoutDto) throws NotFoundException;

    CheckoutDto getCheckoutById(int id) throws NotFoundException;

    List<CheckoutDto> getAllCheckouts();

    Checkout updateCheckout(CheckoutDto checkoutDto, int id) throws NotFoundException;

    void deleteCheckoutById(int id) throws NotFoundException;
}
