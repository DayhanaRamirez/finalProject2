package com.training.program.finalproject2.service.interfaces;

import com.training.program.finalproject2.dto.ProductDto;
import com.training.program.finalproject2.dto.UserPaymentMethodDto;
import com.training.program.finalproject2.entity.Product;
import com.training.program.finalproject2.entity.UserPaymentMethod;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.exception.ProductNameException;

import java.util.List;

public interface UserPaymentMethodService {
    UserPaymentMethod createUserPaymentMethod(UserPaymentMethodDto userPaymentMethodDto) throws NotFoundException ;

    UserPaymentMethodDto getUserPaymentMethodById(int id) throws NotFoundException;

    List<UserPaymentMethodDto> getAllUserPaymentMethod();

    UserPaymentMethod updateUserPaymentMethod(UserPaymentMethodDto userPaymentMethodDto, int id) throws NotFoundException;

    void deleteUserPaymentMethodById(int id) throws NotFoundException;
    void setActiveUserPaymentMethod(int id) throws NotFoundException;
}
