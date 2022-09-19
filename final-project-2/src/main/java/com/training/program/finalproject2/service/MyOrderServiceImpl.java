package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.MyOrderDto;
import com.training.program.finalproject2.dto.ProductDto;
import com.training.program.finalproject2.entity.Checkout;
import com.training.program.finalproject2.entity.MyOrder;
import com.training.program.finalproject2.entity.Product;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.mapper.MyOrderMapper;
import com.training.program.finalproject2.repository.CheckoutRepository;
import com.training.program.finalproject2.repository.MyOrderRepository;
import com.training.program.finalproject2.service.interfaces.MyOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class MyOrderServiceImpl implements MyOrderService {

    private final MyOrderRepository myOrderRepository;
    private final MyOrderMapper myOrderMapper;

    private final CheckoutRepository checkoutRepository;

    @Override
    public MyOrderDto getMyOrderById(int id) throws NotFoundException {
        try{
            return myOrderMapper.myOrderEntityToDto(myOrderRepository.getReferenceById(id));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Couldn't find an order with the given id");
        }
    }

    @Override
    public List<MyOrderDto> getAllMyOrder() {
        List<MyOrder> myOrders = myOrderRepository.findAll();
        List<MyOrderDto> myOrderDtoList = new ArrayList<>();
        for (MyOrder myOrder : myOrders) {
            myOrderDtoList.add(myOrderMapper.myOrderEntityToDto(myOrder));
        }
        return myOrderDtoList;
    }

    @Override
    public MyOrder createMyOrder(MyOrderDto myOrderDto) throws NotFoundException {
        try{
            Checkout checkout = checkoutRepository.getReferenceById(myOrderDto.getIdCheckout());
            MyOrder myOrder = myOrderMapper.myOrderDtoToEntity(myOrderDto, checkout);
            return myOrderRepository.save(myOrder);
        } catch (EntityNotFoundException e){
            throw new NotFoundException("Couldn't find an order with the given id");
        }
    }

    @Override
    public MyOrder updateMyOrder(MyOrderDto myOrderDto, int id) throws NotFoundException {
        try {
            MyOrder myOrder = myOrderRepository.getReferenceById(id);
            Checkout checkout = checkoutRepository.getReferenceById(myOrderDto.getIdCheckout());
            myOrder.setDate(myOrder.getDate());
            myOrder.setCheckout(checkout);
            return myOrderRepository.save(myOrder);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Couldn't find an order with the given IDs");
        }
    }

    @Override
    public void deleteMyOrderById(int id) throws NotFoundException {
        try{
            myOrderRepository.deleteById(id);
        }catch (Exception e){
            throw new NotFoundException("Couldn't find an order product with the given id");
        }

    }
}
