package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.ProductDto;
import com.training.program.finalproject2.dto.UserPaymentMethodDto;
import com.training.program.finalproject2.entity.*;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.exception.ProductNameException;
import com.training.program.finalproject2.mapper.UserPaymentMethodMapper;
import com.training.program.finalproject2.repository.CardTypeRepository;
import com.training.program.finalproject2.repository.CustomerRepository;
import com.training.program.finalproject2.repository.PaymentMethodRepository;
import com.training.program.finalproject2.repository.UserPaymentMethodRepository;
import com.training.program.finalproject2.service.interfaces.UserPaymentMethodService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UserPaymentMethodServiceImpl implements UserPaymentMethodService {
    private final UserPaymentMethodRepository userPaymentMethodRepository;
    private final UserPaymentMethodMapper userPaymentMethodMapper;
    private final CardTypeRepository cardTypeRepository;
    private final CustomerRepository customerRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public UserPaymentMethodDto getUserPaymentMethodById(int id) throws NotFoundException {
        try{
            return userPaymentMethodMapper.userPaymentMethodEntityToDto(userPaymentMethodRepository.getReferenceById(id));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Couldn't find an userPaymentMethod with the given id");
        }
    }

    @Override
    public List<UserPaymentMethodDto> getAllUserPaymentMethod() {
        List<UserPaymentMethod> userPaymentMethodList = userPaymentMethodRepository.findAll();
        List<UserPaymentMethodDto> userPaymentMethodDtoList = new ArrayList<>();
        for (UserPaymentMethod userPaymentMethod : userPaymentMethodList) {
            userPaymentMethodDtoList.add(userPaymentMethodMapper.userPaymentMethodEntityToDto(userPaymentMethod));
        }
        return userPaymentMethodDtoList;

    }

    @Override
    public UserPaymentMethod createUserPaymentMethod(UserPaymentMethodDto userPaymentMethodDto) throws NotFoundException  {
        try{
            CardType cardType = cardTypeRepository.getReferenceById(userPaymentMethodDto.getIdCardType());
            Customer customer = customerRepository.getReferenceById(userPaymentMethodDto.getIdCustomer());
            PaymentMethod paymentMethod = paymentMethodRepository.getReferenceById(userPaymentMethodDto.getIdPaymentMethod());
            UserPaymentMethod userPaymentMethod = userPaymentMethodMapper.userPaymentMethodDtoToEntity(userPaymentMethodDto, cardType, customer, paymentMethod );
            return userPaymentMethodRepository.save(userPaymentMethod);
        } catch (EntityNotFoundException e){
            throw new NotFoundException("Couldn't find an userPaymentMethod  with the given IDs");
        }
    }

    @Override
    public UserPaymentMethod updateUserPaymentMethod(UserPaymentMethodDto userPaymentMethodDto, int id) throws NotFoundException {
        try {
            UserPaymentMethod userPaymentMethod = userPaymentMethodRepository.getReferenceById(id);
            CardType cardType = cardTypeRepository.getReferenceById(userPaymentMethodDto.getIdCardType());
            Customer customer = customerRepository.getReferenceById(userPaymentMethodDto.getIdCustomer());
            PaymentMethod paymentMethod = paymentMethodRepository.getReferenceById(userPaymentMethodDto.getIdPaymentMethod());
            userPaymentMethod.setCardNumber(userPaymentMethodDto.getCardNumber());
            userPaymentMethod.setActive(userPaymentMethodDto.isActive());
            userPaymentMethod.setCardType(cardType);
            userPaymentMethod.setCustomer(customer);
            userPaymentMethod.setPaymentMethod(paymentMethod);
            return userPaymentMethodRepository.save(userPaymentMethod);

        }catch (EntityNotFoundException e){
            throw new NotFoundException("Couldn't find an userPaymentMethod  with the given IDs");
        }
    }

    @Override
    public void deleteUserPaymentMethodById(int id) throws NotFoundException {
        try{
            userPaymentMethodRepository.deleteById(id);
        }catch (Exception e){
            throw new NotFoundException("Couldn't find an userPaymentMethod with the given id");
        }
    }
}
