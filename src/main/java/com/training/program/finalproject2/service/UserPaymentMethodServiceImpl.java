package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.UserPaymentMethodDto;
import com.training.program.finalproject2.entity.CardType;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.entity.PaymentMethod;
import com.training.program.finalproject2.entity.UserPaymentMethod;
import com.training.program.finalproject2.exception.EntityAlreadyExits;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.mapper.UserPaymentMethodMapper;
import com.training.program.finalproject2.repository.CardTypeRepository;
import com.training.program.finalproject2.repository.CustomerRepository;
import com.training.program.finalproject2.repository.PaymentMethodRepository;
import com.training.program.finalproject2.repository.UserPaymentMethodRepository;
import com.training.program.finalproject2.service.interfaces.UserPaymentMethodService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
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
    public UserPaymentMethod createUserPaymentMethod(UserPaymentMethodDto userPaymentMethodDto) throws NotFoundException {
        try {
            CardType cardType = cardTypeRepository.getReferenceById(userPaymentMethodDto.getIdCardType());
            Customer customer = customerRepository.getReferenceById(userPaymentMethodDto.getIdCustomer());
            PaymentMethod paymentMethod = paymentMethodRepository.getReferenceById(userPaymentMethodDto.getIdPaymentMethod());

            checkEntity(cardType, customer, paymentMethod, userPaymentMethodDto.getCardNumber());

            List<UserPaymentMethod> list = userPaymentMethodRepository.findByCustomer(customer);
            UserPaymentMethod userPaymentMethod = userPaymentMethodMapper.userPaymentMethodDtoToEntity(userPaymentMethodDto, cardType, customer, paymentMethod);

            if (list.isEmpty()) {
                userPaymentMethod.setSelectedPaymentMethod(true);
            } else {
                userPaymentMethod.setSelectedPaymentMethod(false);
            }

            return userPaymentMethodRepository.save(userPaymentMethod);
        } catch (Exception e) {
            if (e.getClass() == DataIntegrityViolationException.class) {
                throw new EntityNotFoundException("Couldn't find card type, customer or payment method with said IDs");
            } else {
                throw new EntityAlreadyExits("User already has this payment method");
            }
        }
    }

    private void checkEntity(CardType cardType, Customer customer, PaymentMethod paymentMethod, String cardNumber) {
        try {
            if (userPaymentMethodRepository.findByCardTypeAndCustomerAndPaymentMethodAndCardNumber(cardType, customer, paymentMethod, cardNumber) != null) {
                throw new EntityAlreadyExits("Customer already has a payment method with that card type");
            }
        } catch (Exception e) {
            throw new EntityAlreadyExits("A payment method with that card type and number already exist");
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
            userPaymentMethod.setSelectedPaymentMethod(userPaymentMethodDto.isActive());
            userPaymentMethod.setCardType(cardType);
            userPaymentMethod.setCustomer(customer);
            userPaymentMethod.setPaymentMethod(paymentMethod);
            return userPaymentMethodRepository.save(userPaymentMethod);

        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Couldn't find an userPaymentMethod  with the given IDs");
        }
    }

    @Override
    public void deleteUserPaymentMethodById(int id) throws NotFoundException {
        try {
            userPaymentMethodRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException("Couldn't find an userPaymentMethod with the given id");
        }
    }

    @Override
    public void setActiveUserPaymentMethod(int id) throws NotFoundException {
        try {
            UserPaymentMethod oldPaymentMethod = userPaymentMethodRepository.findBySelectedPaymentMethodTrue();
            oldPaymentMethod.setSelectedPaymentMethod(false);

            UserPaymentMethod newPaymentMethod = userPaymentMethodRepository.getReferenceById(id);
            newPaymentMethod.setSelectedPaymentMethod(true);

            userPaymentMethodRepository.save(oldPaymentMethod);
            userPaymentMethodRepository.save(newPaymentMethod);
        } catch (Exception e) {
            throw new NotFoundException("Couldn't find an addressCustomer with the given id");
        }
    }
}
