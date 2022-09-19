package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.CheckoutDto;
import com.training.program.finalproject2.dto.PaymentMethodDto;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.exception.PaymentMethodTypeAlreadyExistException;
import com.training.program.finalproject2.service.interfaces.CheckoutService;
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
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @GetMapping("/{id}")
    public ResponseEntity<CheckoutDto> getCheckoutId(@PathVariable("id") int id) throws NotFoundException {
        return new ResponseEntity<>(checkoutService.getCheckoutById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CheckoutDto>> getAllCheckouts(){
        return new ResponseEntity<>(checkoutService.getAllCheckouts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createCheckout(@Valid @RequestBody CheckoutDto checkoutDto) throws NotFoundException {
        checkoutService.createCheckout(checkoutDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCheckout(@PathVariable("id") int id, @Valid @RequestBody CheckoutDto checkoutDto)  throws NotFoundException {
        checkoutService.updateCheckout(checkoutDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCheckout(@PathVariable("id") int id) throws NotFoundException {
        checkoutService.getCheckoutById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
