package com.training.program.finalproject2.service.interfaces;

import com.training.program.finalproject2.dto.MyOrderDto;
import com.training.program.finalproject2.dto.ProductDto;
import com.training.program.finalproject2.entity.MyOrder;
import com.training.program.finalproject2.entity.Product;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.exception.ProductNameException;

import java.util.List;

public interface MyOrderService {
    MyOrder createMyOrder(MyOrderDto myOrderDto) throws NotFoundException ;

    MyOrderDto getMyOrderById(int id) throws NotFoundException;

    List<MyOrderDto> getAllMyOrder();

    MyOrder updateMyOrder(MyOrderDto myOrderDto, int id) throws NotFoundException;

    void deleteMyOrderById(int id) throws NotFoundException;
}
