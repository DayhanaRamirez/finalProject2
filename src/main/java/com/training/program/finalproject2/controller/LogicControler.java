package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.AddressCustomerDto;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.request.CheckOutRequest;
import com.training.program.finalproject2.service.interfaces.LogicService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/logic")
public class LogicControler {

    private final LogicService logicService;

    @PostMapping
    public ResponseEntity<AddressCustomerDto> startCheckout(@Valid @RequestBody CheckOutRequest checkOutRequest) throws NotFoundException {
        logicService.startCheckout(checkOutRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
