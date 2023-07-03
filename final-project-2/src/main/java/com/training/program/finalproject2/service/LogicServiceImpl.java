package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.AddressCustomerDto;
import com.training.program.finalproject2.dto.CheckoutDto;
import com.training.program.finalproject2.dto.CustomerDto;
import com.training.program.finalproject2.dto.ProductQuantityDto;
import com.training.program.finalproject2.entity.AddressCustomer;
import com.training.program.finalproject2.entity.Checkout;
import com.training.program.finalproject2.entity.ProductQuantity;
import com.training.program.finalproject2.mapper.AddressCustomerMapper;
import com.training.program.finalproject2.mapper.CheckoutMapper;
import com.training.program.finalproject2.mapper.ProductQuantityMapper;
import com.training.program.finalproject2.repository.AddressCustomerRepository;
import com.training.program.finalproject2.repository.CheckoutRepository;
import com.training.program.finalproject2.repository.ProductQuantityRepository;
import com.training.program.finalproject2.request.CheckOutRequest;
import com.training.program.finalproject2.response.CompleteCheckoutResponse;
import com.training.program.finalproject2.service.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class LogicServiceImpl implements LogicService {

    private final CheckoutService checkoutService;;
    private final CheckoutRepository checkoutRepository;
    private final CustomerService customerService;
    private final ProductQuantityService productQuantityService;
    private final ProductQuantityRepository productQuantityRepository;
    private final ProductQuantityMapper productQuantityMapper;
    private final AddressCustomerRepository addressCustomerRepository;
    private final AddressCustomerMapper addressCustomerMapper;
    private final ProductService productService;

    public void startCheckout(CheckOutRequest checkOutRequest){
        CheckoutDto checkoutDto = CheckoutDto.builder()
                .date(LocalDate.now().toString())
                .idCustomer(checkOutRequest.getIdCustomer())
                .build();
        Checkout checkout = checkoutService.createCheckout(checkoutDto);
        ProductQuantityDto productQuantityDto = ProductQuantityDto.builder()
                .quantity(checkOutRequest.getQuantity())
                .idCheckout(checkout.getId())
                .idProduct(checkOutRequest.getIdProduct())
                .build();
        productQuantityService.createProductQuantity(productQuantityDto);
    }

    public CompleteCheckoutResponse endCheckout(int id){
        CompleteCheckoutResponse endCheckoutResponse = new CompleteCheckoutResponse();
        CheckoutDto checkoutDto = checkoutService.getCheckoutById(id);
        Checkout checkout = checkoutRepository.getReferenceById(id);
        
        CustomerDto customerDto = customerService.getCustomerById(checkoutDto.getIdCustomer());
        List<ProductQuantity> productQuantityList = productQuantityRepository.findByCheckout(checkout);

        AddressCustomer addressCustomer = addressCustomerRepository.findBySelectedAddressTrue();
        AddressCustomerDto addressCustomerDto = addressCustomerMapper.addressCustomerEntityToAddressCustomerDto(addressCustomer);

        endCheckoutResponse.setCheckoutDto(checkoutDto);
        endCheckoutResponse.setCustomerDto(customerDto);
        endCheckoutResponse.setAddressCustomerDto(addressCustomerDto);
        for (ProductQuantity productQuantity: productQuantityList) {
            endCheckoutResponse.getProductQuantityListDto().add(productQuantityMapper.productQuantityEntityToDto(productQuantity));
        }

        return endCheckoutResponse;
    }
}
