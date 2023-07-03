package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.AddressDto;
import com.training.program.finalproject2.entity.Address;
import com.training.program.finalproject2.exception.AddressAlreadyExistsException;
import com.training.program.finalproject2.mapper.AddressMapper;
import com.training.program.finalproject2.repository.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;

    private AddressDto addressDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.address = Address.builder()
                .country("Colombia")
                .state("Antioquia")
                .city("Medellin")
                .street("Calle 2")
                .id(1).build();

        this.addressDto = AddressDto.builder()
                .country("Colombia")
                .state("Antioquia")
                .city("Medellin")
                .street("Calle 2").build();
    }

    @Test
    void getAddressByIdIsSuccessful() {

        //Arrange
        when(addressRepository.getReferenceById(anyInt())).thenReturn(address);
        when(addressMapper.addressEntityToAddressDto(any(Address.class))).thenReturn(addressDto);

        //Act
        AddressDto response = addressService.getAddressById(1);

        //Assert
        verify(addressRepository).getReferenceById(anyInt());
        verify(addressMapper).addressEntityToAddressDto(any(Address.class));
    }

    @Test
    void getAddressByIdDoesNotExist(){

        //Arrange
        when(addressRepository.getReferenceById(anyInt())).thenThrow(EntityNotFoundException.class);

        //Act
        final EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            addressService.getAddressById(anyInt());
        });

        //Assert
        verify(addressRepository).getReferenceById(anyInt());
        verify(addressMapper, times(0)).addressEntityToAddressDto(any(Address.class));
        assertAll(
                () -> assertThat(exception.getClass(), is(EntityNotFoundException.class)),
                () -> assertThat(exception.getMessage(), is("Couldn't find an address with the given id"))
        );
    }

    @Test
    void getAllAddresses() {

        //Arrange
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);

        when(addressRepository.findAll()).thenReturn(addresses);
        when(addressMapper.addressEntityToAddressDto(addresses.get(0))).thenReturn(addressDto);

        //Act
        List<AddressDto> response = addressService.getAllAddresses();

        //Assert
        verify(addressRepository).findAll();
        verify(addressMapper).addressEntityToAddressDto(any(Address.class));
        assertAll(
                () -> assertThat(response.size(), is(1))
        );
    }

    @Test
    void createAddressIsSuccessful() {
        //Act
        when(addressMapper.addressDtoToAddressEntity(any(AddressDto.class))).thenReturn(address);
        when(addressRepository.save(any(Address.class))).thenReturn(any(Address.class));
        when(addressRepository.existsByStreetAndCityAndStateAndCountry(
                eq(address.getStreet()),
                eq(address.getCity()),
                eq(address.getState()),
                address.getCountry())).thenReturn(false);

        //Act
        Address response = addressService.createAddress(addressDto);

        //Arrange
        verify(addressRepository).save(any(Address.class));
        verify(addressMapper).addressDtoToAddressEntity(any(AddressDto.class));
        verify(addressRepository).existsByStreetAndCityAndStateAndCountry(
                eq(address.getStreet()),
                eq(address.getCity()),
                eq(address.getState()),
                eq(address.getCountry()));
    }

    @Test
    void createAddressAlreadyExists() {

        //Act
        when(addressMapper.addressDtoToAddressEntity(any(AddressDto.class))).thenReturn(address);
        when(addressRepository.existsByStreetAndCityAndStateAndCountry(
                eq(address.getStreet()),
                eq(address.getCity()),
                eq(address.getState()),
                eq(address.getCountry()))).thenReturn(true);

        //Act
        final AddressAlreadyExistsException exception = Assertions.assertThrows(AddressAlreadyExistsException.class, () -> {
            addressService.createAddress(addressDto);
        });

        //Arrange
        verify(addressMapper).addressDtoToAddressEntity(any(AddressDto.class));
        verify(addressRepository).existsByStreetAndCityAndStateAndCountry(
                eq(address.getStreet()),
                eq(address.getCity()),
                eq(address.getState()),
                eq(address.getCountry()));
        assertAll(
                () -> assertThat(exception.getClass(), is(AddressAlreadyExistsException.class)),
                () -> assertThat(exception.getMessage(), is("Address already exists"))
        );
    }

    @Test
    void updateAddressIsSuccessful() {

        //Arrange
        addressDto.setCountry("Brazil");
        when(addressMapper.addressDtoToAddressEntity(any(AddressDto.class))).thenReturn(address);
        when(addressRepository.existsByStreetAndCityAndStateAndCountry(
                eq(addressDto.getStreet()),
                eq(addressDto.getCity()),
                eq(addressDto.getState()),
                eq(addressDto.getCountry()))).thenReturn(false);
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        //Act
        Address response = addressService.updateAddress(addressDto, 1);

        //Assert
        verify(addressMapper).addressDtoToAddressEntity(any(AddressDto.class));
        verify(addressRepository).existsByStreetAndCityAndStateAndCountry(
                eq(addressDto.getStreet()),
                eq(addressDto.getCity()),
                eq(addressDto.getState()),
                eq(addressDto.getCountry()));
        assertAll(
                () -> assertThat(response.getCountry(), is("Brazil"))
        );
    }

    @Test
    void updateAddressIsNotSuccessful() {

        //Arrange
        addressDto.setCountry("Brazil");
        when(addressMapper.addressDtoToAddressEntity(any(AddressDto.class))).thenReturn(address);
        when(addressRepository.existsByStreetAndCityAndStateAndCountry(
                eq(addressDto.getStreet()),
                eq(addressDto.getCity()),
                eq(addressDto.getState()),
                eq(addressDto.getCountry()))).thenReturn(true);

        //Act
        final AddressAlreadyExistsException exception = Assertions.assertThrows(AddressAlreadyExistsException.class, () -> {
            addressService.updateAddress(addressDto, 1);
        });

        //Assert
        verify(addressMapper).addressDtoToAddressEntity(any(AddressDto.class));
        verify(addressRepository).existsByStreetAndCityAndStateAndCountry(
                eq(addressDto.getStreet()),
                eq(addressDto.getCity()),
                eq(addressDto.getState()),
                eq(addressDto.getCountry())
        );
        assertAll(
                () -> assertThat(exception.getClass(), is(AddressAlreadyExistsException.class)),
                () -> assertThat(exception.getMessage(), is("Address already exists"))
        );
    }

    @Test
    void deleteAddressByIdIsSuccessful() {
        //Arrange
        doNothing().when(addressRepository).deleteById(anyInt());

        //Act
        addressService.deleteAddressById(1);

        //Assert
        verify(addressRepository).deleteById(anyInt());
    }

    @Test
    void deleteAddressByIdIsNotSuccessful() {
        //Arrange
        doThrow(EntityNotFoundException.class).when(addressRepository).deleteById(anyInt());

        //Act
        final EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            addressService.deleteAddressById(1);
        });

        //Assert
        verify(addressRepository).deleteById(anyInt());
        assertAll(
                () -> assertThat(exception.getClass(), is(EntityNotFoundException.class)),
                () -> assertThat(exception.getMessage(), is("Couldn't find a address with the given id"))
        );
    }
}