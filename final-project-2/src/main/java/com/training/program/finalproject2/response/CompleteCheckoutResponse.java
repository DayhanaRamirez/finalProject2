package com.training.program.finalproject2.response;

import com.training.program.finalproject2.dto.AddressCustomerDto;
import com.training.program.finalproject2.dto.CheckoutDto;
import com.training.program.finalproject2.dto.CustomerDto;
import com.training.program.finalproject2.dto.ProductQuantityDto;
import com.training.program.finalproject2.entity.AddressCustomer;
import com.training.program.finalproject2.entity.Checkout;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.entity.ProductQuantity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompleteCheckoutResponse {

    private CheckoutDto checkoutDto;
    private List<ProductQuantityDto> productQuantityListDto;
    private AddressCustomerDto addressCustomerDto;
    private CustomerDto customerDto;

}
