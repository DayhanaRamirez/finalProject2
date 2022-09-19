package com.training.program.finalproject2.service.interfaces;

import com.training.program.finalproject2.dto.AddressCustomerDto;
import com.training.program.finalproject2.dto.AddressDto;
import com.training.program.finalproject2.entity.Address;
import com.training.program.finalproject2.entity.AddressCustomer;
import com.training.program.finalproject2.exception.AddressAlreadyExistsException;
import com.training.program.finalproject2.exception.NotFoundException;

import java.util.List;

public interface AddressCustomerService {
    AddressCustomer createAddressCustomer (AddressCustomerDto addressCustomerDto) throws NotFoundException;

    AddressCustomerDto getAddressCustomerById(int id) throws NotFoundException;

    List<AddressCustomerDto> getAllAddressCustomer();

    AddressCustomer updateAddressCustomer(AddressCustomerDto addressCustomerDto, int id) throws NotFoundException;

    void deleteAddressCustomerById(int id) throws NotFoundException;
}
