package com.training.program.finalproject2.service.interfaces;

import com.training.program.finalproject2.dto.AddressDto;
import com.training.program.finalproject2.entity.Address;
import com.training.program.finalproject2.exception.AddressAlreadyExistsException;
import com.training.program.finalproject2.exception.NotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface AddressService {
    Address createAddress(AddressDto addressDto) throws AddressAlreadyExistsException;

    AddressDto getAddressById(int id) throws EntityNotFoundException;

    List<AddressDto> getAllAddresses();

    Address updateAddress(AddressDto addressDto, int id) throws AddressAlreadyExistsException;

    void deleteAddressById(int id) throws NotFoundException;
}
