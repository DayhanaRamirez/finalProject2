package com.training.program.finalproject2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(value = CreateUserEmailException.class)
    public ResponseEntity<Object> handleCreateUserEmailException(CreateUserEmailException e) {
        ApiException apiException = ApiException.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .zonedDateTime(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException e) {
        ApiException apiException = ApiException.builder()
                .message(e.getMessage())
                .httpStatus(NOT_FOUND)
                .zonedDateTime(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(apiException, NOT_FOUND);
    }

    @ExceptionHandler(value = AddressAlreadyExistsException.class)
    public ResponseEntity<Object> handleAddressAlreadyExistsException(AddressAlreadyExistsException e) {
        ApiException apiException = ApiException.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .zonedDateTime(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PaymentMethodTypeAlreadyExistException.class)
    public ResponseEntity<Object> handlePaymentMethodTypeAlreadyExistException(PaymentMethodTypeAlreadyExistException e) {
        ApiException apiException = ApiException.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .zonedDateTime(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ProductNameException.class)
    public ResponseEntity<Object> handleProductNameException(ProductNameException e) {
        ApiException apiException = ApiException.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .zonedDateTime(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CardTypeAlreadyExistException.class)
    public ResponseEntity<Object> handleCardTypeAlreadyExistException(CardTypeAlreadyExistException e) {
        ApiException apiException = ApiException.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .zonedDateTime(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}

