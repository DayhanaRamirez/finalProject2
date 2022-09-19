package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.AddressDto;
import com.training.program.finalproject2.entity.Address;
import com.training.program.finalproject2.exception.AddressAlreadyExistsException;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.mapper.AddressMapper;
import com.training.program.finalproject2.repository.AddressRepository;
import com.training.program.finalproject2.service.interfaces.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public AddressDto getAddressById(int id) throws NotFoundException {
        try{
            return addressMapper.addressEntityToAddressDto(addressRepository.getReferenceById(id));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Couldn't find an address with the given id");
        }
    }

    @Override
    public List<AddressDto> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        List<AddressDto> addressDtoList = new ArrayList<>();
        for (Address address : addresses) {
            addressDtoList.add(addressMapper.addressEntityToAddressDto(address));
        }
        return addressDtoList;
    }

    @Override
    public Address createAddress(AddressDto addressDto) throws AddressAlreadyExistsException {
        Address address = addressMapper.addressDtoToAddressEntity(addressDto);
        if (addressRepository.existsByStreetAndCityAndStateAndCountry(address.getStreet(),
                address.getCity(), address.getState(), address.getCountry())){
            throw new AddressAlreadyExistsException("Address already exists");
        }

        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(AddressDto addressDto, int id) throws AddressAlreadyExistsException {
        try {
            Address address = addressMapper.addressDtoToAddressEntity(addressDto);
            if(addressRepository.existsByStreetAndCityAndStateAndCountry(addressDto.getStreet(), addressDto.getCity(), addressDto.getState(), addressDto.getCountry())){
                throw new AddressAlreadyExistsException("Address already exists");
            }

            address.setStreet(addressDto.getStreet());
            address.setCity(addressDto.getCity());
            address.setState(addressDto.getState());
            address.setCountry(addressDto.getCountry());
            return  addressRepository.save(address);

        } catch(EntityNotFoundException e) {
            throw new NotFoundException("Couldn't find a address with the given id");
        }
    }

    @Override
    public void deleteAddressById(int id) throws NotFoundException {
        try {
            addressRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException("Couldn't find a address with the given id");
        }
    }
}
