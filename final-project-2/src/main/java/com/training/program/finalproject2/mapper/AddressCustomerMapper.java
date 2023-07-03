package com.training.program.finalproject2.mapper;

import com.training.program.finalproject2.dto.AddressCustomerDto;
import com.training.program.finalproject2.entity.Address;
import com.training.program.finalproject2.entity.AddressCustomer;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.repository.AddressRepository;
import com.training.program.finalproject2.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class AddressCustomerMapper {

    public AddressCustomer addressCustomerDtoToAddressCustomerEntity(AddressCustomerDto addressCustomerDto, Address address, Customer customer){
        return AddressCustomer.builder()
                .selectedAddress(addressCustomerDto.isActive())
                .address(address)
                .customer(customer)
                .build();
    }

    public AddressCustomerDto addressCustomerEntityToAddressCustomerDto(AddressCustomer addressCustomer){
        return AddressCustomerDto.builder().active(addressCustomer.isSelectedAddress())
                .build();
    }
}
