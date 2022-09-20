package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.PaymentMethodDto;
import com.training.program.finalproject2.entity.PaymentMethod;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.exception.PaymentMethodTypeAlreadyExistException;
import com.training.program.finalproject2.mapper.PaymentMethodMapper;
import com.training.program.finalproject2.repository.PaymentMethodRepository;
import com.training.program.finalproject2.service.interfaces.PaymentMethodService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    @Override
    public PaymentMethodDto getPaymentMethodDtoById(int id) throws NotFoundException {
        try {
            return paymentMethodMapper.paymentMethodEntityToPaymentMethodDto(paymentMethodRepository.getReferenceById(id));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Couldn't find a payment method with the given id");
        }
    }

    @Override
    public List<PaymentMethodDto> getAllPaymentMethods() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
        List<PaymentMethodDto> paymentMethodDtoList = new ArrayList<>();
        for (PaymentMethod paymentMethod : paymentMethods) {
            paymentMethodDtoList.add(paymentMethodMapper.paymentMethodEntityToPaymentMethodDto(paymentMethod));
        }
        return paymentMethodDtoList;
    }

    @Override
    public PaymentMethod createPaymentMethod(PaymentMethodDto paymentMethodDto) throws PaymentMethodTypeAlreadyExistException {
        PaymentMethod paymentMethod = paymentMethodMapper.paymentMethodDtoToPaymentMethodEntity(paymentMethodDto);
        if (paymentMethodRepository.findPaymentMethodByType(paymentMethod.getType()) != null) {
            throw new PaymentMethodTypeAlreadyExistException("Payment method type already exist");
        }

        return paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public PaymentMethod updatePaymentMethod(PaymentMethodDto paymentMethodDto, int id) throws PaymentMethodTypeAlreadyExistException {
        try {
            PaymentMethod paymentMethod = paymentMethodRepository.getReferenceById(id);
            if (paymentMethodRepository.findPaymentMethodByType(paymentMethodDto.getType()) != null) {
                throw new PaymentMethodTypeAlreadyExistException("Payment method type already exist");
            }
            paymentMethod.setType(paymentMethod.getType());
            return paymentMethodRepository.save(paymentMethod);

        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Couldn't find a payment method with the given id");
        }
    }

    @Override
    public void deletePaymentMethodById(int id) throws NotFoundException {
        try {
            paymentMethodRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException("Couldn't find a payment method with the given id");
        }

    }
}
