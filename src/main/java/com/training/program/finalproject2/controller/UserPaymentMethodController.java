package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.UserPaymentMethodDto;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.service.interfaces.UserPaymentMethodService;
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
@RequestMapping("/userpaymentmethod")
public class UserPaymentMethodController {
    private final UserPaymentMethodService userPaymentMethodService;

    @GetMapping("/{id}")
    public ResponseEntity<UserPaymentMethodDto> getUserPaymentMethodById(@PathVariable("id") int id) throws NotFoundException {
        return new ResponseEntity<>(userPaymentMethodService.getUserPaymentMethodById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserPaymentMethodDto>> getAllUserPaymentMethod() {
        return new ResponseEntity<>(userPaymentMethodService.getAllUserPaymentMethod(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createUserPaymentMethod(@Valid @RequestBody UserPaymentMethodDto userPaymentMethodDto) throws NotFoundException {
        userPaymentMethodService.createUserPaymentMethod(userPaymentMethodDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateUserPaymentMethod(@PathVariable("id") int id, @Valid @RequestBody UserPaymentMethodDto userPaymentMethodDto) throws NotFoundException {
        userPaymentMethodService.updateUserPaymentMethod(userPaymentMethodDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserPaymentMethod(@PathVariable("id") int id) throws NotFoundException {
        userPaymentMethodService.deleteUserPaymentMethodById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/active/{id_paymentmethod}")
    public ResponseEntity<HttpStatus> setActiveUserPaymentMethod(@PathVariable("id_paymentmethod") int id) throws NotFoundException {
        userPaymentMethodService.setActiveUserPaymentMethod(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
