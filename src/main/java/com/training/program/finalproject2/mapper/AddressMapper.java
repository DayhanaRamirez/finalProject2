package com.training.program.finalproject2.mapper;

import com.training.program.finalproject2.dto.AddressDto;
import com.training.program.finalproject2.entity.Address;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class AddressMapper {
    public Address addressDtoToAddressEntity(AddressDto addressDto) {
        return Address.builder()
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .state(addressDto.getState())
                .country(addressDto.getCountry())
                .build();
    }

    public AddressDto addressEntityToAddressDto(Address address) {
        return AddressDto.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .build();
    }

}
