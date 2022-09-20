package com.training.program.finalproject2.service.interfaces;

import com.training.program.finalproject2.dto.AddressDto;
import com.training.program.finalproject2.dto.ProductQuantityDto;
import com.training.program.finalproject2.entity.Address;
import com.training.program.finalproject2.entity.ProductQuantity;
import com.training.program.finalproject2.exception.AddressAlreadyExistsException;
import com.training.program.finalproject2.exception.NotFoundException;

import java.util.List;

public interface ProductQuantityService {
    ProductQuantity createProductQuantity (ProductQuantityDto productQuantityDto) throws NotFoundException;

    ProductQuantityDto getProductQuantityById(int id) throws NotFoundException;

    List<ProductQuantityDto> getAllProductQuantity ();

    ProductQuantity updateProductQuantity(ProductQuantityDto productQuantityDto, int id) throws NotFoundException;

    void deleteProductQuantityById(int id) throws NotFoundException;
    void modifyProductQuantityById(ProductQuantityDto productQuantityDto) throws NotFoundException;
}
