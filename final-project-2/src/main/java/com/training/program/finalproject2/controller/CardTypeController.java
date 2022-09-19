package com.training.program.finalproject2.controller;

import com.training.program.finalproject2.dto.CardTypeDto;
import com.training.program.finalproject2.exception.CardTypeAlreadyExistException;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.service.interfaces.CardTypeService;
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
@RequestMapping("/cardtype")
public class CardTypeController {

    private final CardTypeService cardTypeService;

    @GetMapping("/{id}")
    public ResponseEntity<CardTypeDto> getCardTypById(@PathVariable("id") int id) throws NotFoundException {
        return new ResponseEntity<>(cardTypeService.getCardTypeById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CardTypeDto>> getAllCardTypes() {
        return new ResponseEntity<>(cardTypeService.getAllCardTypes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createCardType(@Valid @RequestBody CardTypeDto cardTypeDto) throws CardTypeAlreadyExistException {
        cardTypeService.createCardType(cardTypeDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCardType(@PathVariable("id") int id, @Valid @RequestBody CardTypeDto cardTypeDto) throws CardTypeAlreadyExistException {
        cardTypeService.updateCardType(cardTypeDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCardType(@PathVariable("id") int id) throws NotFoundException {
        cardTypeService.deleteCardTypeId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
