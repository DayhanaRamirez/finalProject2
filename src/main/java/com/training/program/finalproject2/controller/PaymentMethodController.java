package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.PaymentMethodDto;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.exception.PaymentMethodTypeAlreadyExistException;
import com.training.program.finalproject2.service.interfaces.PaymentMethodService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/paymentmethod")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodDto> getPaymentMethodById(@PathVariable("id") int id) throws NotFoundException {
        return new ResponseEntity<>(paymentMethodService.getPaymentMethodDtoById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PaymentMethodDto>> getAllPaymentMethods() {
        return new ResponseEntity<>(paymentMethodService.getAllPaymentMethods(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createPaymentMethod(@Valid @RequestBody PaymentMethodDto paymentMethodDto) throws PaymentMethodTypeAlreadyExistException {
        paymentMethodService.createPaymentMethod(paymentMethodDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updatePaymentMethod(@PathVariable("id") int id, @Valid @RequestBody PaymentMethodDto paymentMethodDto) throws PaymentMethodTypeAlreadyExistException {
        paymentMethodService.updatePaymentMethod(paymentMethodDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePaymentMethod(@PathVariable("id") int id) throws NotFoundException {
        paymentMethodService.deletePaymentMethodById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
