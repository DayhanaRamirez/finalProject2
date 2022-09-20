package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.MyOrderDto;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.service.interfaces.MyOrderService;
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
@RequestMapping("/order")
public class MyOrderController {

    private final MyOrderService myOrderService;

    @GetMapping("/{id}")
    public ResponseEntity<MyOrderDto> getMyOrderById(@PathVariable("id") int id) throws NotFoundException {
        return new ResponseEntity<>(myOrderService.getMyOrderById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MyOrderDto>> getAllMyOrder() {
        return new ResponseEntity<>(myOrderService.getAllMyOrder(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createMyOrder(@Valid @RequestBody MyOrderDto myOrderDto) throws NotFoundException {
        myOrderService.createMyOrder(myOrderDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateMyOrder(@PathVariable("id") int id, @Valid @RequestBody MyOrderDto myOrderDto) throws NotFoundException {
        myOrderService.updateMyOrder(myOrderDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMyOrderById(@PathVariable("id") int id) throws NotFoundException {
        myOrderService.deleteMyOrderById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
