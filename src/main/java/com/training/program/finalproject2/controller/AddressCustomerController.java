package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.AddressCustomerDto;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.service.interfaces.AddressCustomerService;
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
@RequestMapping("/addresscustomer")
public class AddressCustomerController {
    private final AddressCustomerService addressCustomerService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressCustomerDto> getAddressCustomerById(@PathVariable("id") int id) throws NotFoundException {
        return new ResponseEntity<>(addressCustomerService.getAddressCustomerById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AddressCustomerDto>> getAllAddressCustomer() {
        return new ResponseEntity<>(addressCustomerService.getAllAddressCustomer(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createAddressCustomer(@Valid @RequestBody AddressCustomerDto addressCustomerDto) {
        addressCustomerService.createAddressCustomer(addressCustomerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateAddressCustomer(@PathVariable("id") int id, @Valid @RequestBody AddressCustomerDto addressCustomerDto) throws NotFoundException {
        addressCustomerService.updateAddressCustomer(addressCustomerDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAddressCustomer(@PathVariable("id") int id) throws NotFoundException {
        addressCustomerService.deleteAddressCustomerById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/active/{id_address}")
    public ResponseEntity<HttpStatus> setActiveAddressCustomer(@PathVariable("id_address") int id) throws NotFoundException {
        addressCustomerService.setActiveAddressCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
