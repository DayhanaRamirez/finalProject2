package com.training.program.finalproject2.service.interfaces;

import com.training.program.finalproject2.dto.PaymentMethodDto;
import com.training.program.finalproject2.entity.PaymentMethod;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.exception.PaymentMethodTypeAlreadyExistException;

import java.util.List;

public interface PaymentMethodService {
    PaymentMethod createPaymentMethod(PaymentMethodDto paymentMethodDto) throws PaymentMethodTypeAlreadyExistException;

    PaymentMethodDto getPaymentMethodDtoById(int id) throws NotFoundException;

    List<PaymentMethodDto> getAllPaymentMethods();

    PaymentMethod updatePaymentMethod(PaymentMethodDto paymentMethodDto, int id) throws PaymentMethodTypeAlreadyExistException;

    void deletePaymentMethodById(int id) throws NotFoundException;
}
