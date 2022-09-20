package com.training.program.finalproject2.exception;

public class PaymentMethodTypeAlreadyExistException extends RuntimeException {
    public PaymentMethodTypeAlreadyExistException(String message) {
        super(message);
    }
}
