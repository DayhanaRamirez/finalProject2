package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.ProductDto;
import com.training.program.finalproject2.dto.ProductQuantityDto;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.exception.ProductNameException;
import com.training.program.finalproject2.service.interfaces.ProductQuantityService;
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
@RequestMapping("/productquantity")
public class ProductQuantityController {

    private final ProductQuantityService productQuantityService;


    @GetMapping("/{id}")
    public ResponseEntity<ProductQuantityDto> getProductQuantityById(@PathVariable("id") int id) throws NotFoundException {
        return new ResponseEntity<>(productQuantityService.getProductQuantityById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductQuantityDto>> getALlProductQuantity()  {
        return new ResponseEntity<>(productQuantityService.getAllProductQuantity(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createProductQuantity(@Valid @RequestBody ProductQuantityDto productQuantityDto) throws NotFoundException{
        productQuantityService.createProductQuantity(productQuantityDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateProductQuantity(@PathVariable("id") int id, @Valid @RequestBody ProductQuantityDto productQuantityDto) throws NotFoundException {
        productQuantityService.updateProductQuantity(productQuantityDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProductQuantity(@PathVariable("id") int id) throws NotFoundException {
        productQuantityService.deleteProductQuantityById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/modify")
    public ResponseEntity<HttpStatus> modifyProductQuantity(@Valid @RequestBody ProductQuantityDto productQuantityDto) throws NotFoundException {
        productQuantityService.modifyProductQuantityById(productQuantityDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
