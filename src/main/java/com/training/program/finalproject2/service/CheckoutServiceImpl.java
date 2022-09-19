package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.AddressDto;
import com.training.program.finalproject2.dto.CheckoutDto;
import com.training.program.finalproject2.entity.Address;
import com.training.program.finalproject2.entity.Checkout;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.mapper.CheckoutMapper;
import com.training.program.finalproject2.repository.CheckoutRepository;
import com.training.program.finalproject2.repository.CustomerRepository;
import com.training.program.finalproject2.service.interfaces.CheckoutService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final CheckoutMapper checkoutMapper;

    private final CustomerRepository customerRepository;

    @Override
    public CheckoutDto getCheckoutById(int id) throws NotFoundException {
        try{
            return checkoutMapper.checkoutEntityToCheckoutDto(checkoutRepository.getReferenceById(id));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Couldn't find a checkout with the given id");
        }
    }

    @Override
    public List<CheckoutDto> getAllCheckouts() {
        List<Checkout> checkouts = checkoutRepository.findAll();
        List<CheckoutDto> checkoutDtoList = new ArrayList<>();
        for (Checkout checkout : checkouts) {
            checkoutDtoList.add(checkoutMapper.checkoutEntityToCheckoutDto(checkout));
        }
        return checkoutDtoList;
    }

    @Override
    public Checkout createCheckout(CheckoutDto checkoutDto) throws NotFoundException {
        try{
            Customer customer = customerRepository.getReferenceById(checkoutDto.getIdCustomer());
            Checkout checkout = checkoutMapper.checkoutDtoToCheckoutEntity(checkoutDto, customer);
            return checkoutRepository.save(checkout);
        } catch (EntityNotFoundException e){
            throw new NotFoundException("Couldn't find a checkout with the given id");
        }
    }

    @Override
    public Checkout updateCheckout(CheckoutDto checkoutDto, int id) throws NotFoundException {
        try{
            Customer customer = customerRepository.getReferenceById(checkoutDto.getIdCustomer());
            Checkout checkout = checkoutRepository.getReferenceById(id);
            checkout.setDate(checkoutDto.getDate());
            checkout.setCustomer(customer);
            return checkoutRepository.save(checkout);
        } catch(EntityNotFoundException e) {
            throw new NotFoundException("Couldn't find a checkout with the given id");
        }
    }

    @Override
    public void deleteCheckoutById(int id) throws NotFoundException {
        try {
            checkoutRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException("Couldn't find a checkout with the given id");
        }
    }
}
