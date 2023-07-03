package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.AddressCustomerDto;
import com.training.program.finalproject2.dto.AddressDto;
import com.training.program.finalproject2.entity.Address;
import com.training.program.finalproject2.entity.AddressCustomer;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.exception.AddressAlreadyExistsException;
import com.training.program.finalproject2.exception.EntityAlreadyExits;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.mapper.AddressCustomerMapper;
import com.training.program.finalproject2.repository.AddressCustomerRepository;
import com.training.program.finalproject2.repository.AddressRepository;
import com.training.program.finalproject2.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

class AddressCustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressCustomerRepository addressCustomerRepository;

    @Mock
    private AddressCustomerMapper addressCustomerMapper;

    @InjectMocks
    private AddressCustomerServiceImpl addressCustomerService;

    private Customer customer;
    private Address address;
    private AddressCustomer addressCustomer;
    private AddressCustomerDto addressCustomerDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.customer = Customer.builder()
                .email("dayha@gmail.com")
                .lastName("Ramirez")
                .firstName("Dayhana")
                .id(1).build();

        this.address = Address.builder()
                .country("Colombia")
                .state("Antioquia")
                .city("Medellin")
                .street("Calle 2")
                .id(1).build();

        this.addressCustomer = AddressCustomer.builder()
                .customer(customer)
                .address(address)
                .selectedAddress(false).build();

        this.addressCustomerDto = addressCustomerDto.builder()
                .idCustomer(1)
                .idAddress(1)
                .active(false).build();
    }

    @Test
    void getAddressCustomerById() {
        //Arrange
        when(addressCustomerRepository.getReferenceById(anyInt())).thenReturn(addressCustomer);
        when(addressCustomerMapper.addressCustomerEntityToAddressCustomerDto(addressCustomer)).thenReturn(addressCustomerDto);

        //Act
        AddressCustomerDto response = addressCustomerService.getAddressCustomerById(1);

        //Assert
        verify(addressCustomerRepository).getReferenceById(anyInt());
        verify(addressCustomerMapper).addressCustomerEntityToAddressCustomerDto(addressCustomer);
    }

    @Test
    void getAddressCustomerByIdDoesNotExist(){

        //Arrange
        when(addressCustomerRepository.getReferenceById(anyInt())).thenThrow(EntityNotFoundException.class);

        //Act
        final EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            addressCustomerService.getAddressCustomerById(anyInt());
        });

        //Assert
        verify(addressCustomerRepository).getReferenceById(anyInt());
       assertAll(
                () -> assertThat(exception.getClass(), is(EntityNotFoundException.class)),
                () -> assertThat(exception.getMessage(), is("Couldn't find an addressCustomer with the given id"))
        );
    }

    @Test
    void getAllAddressCustomers() {

        //Arrange
        List<AddressCustomer> addressCustomers = new ArrayList<>();
        addressCustomers.add(addressCustomer);

        when(addressCustomerRepository.findAll()).thenReturn(addressCustomers);
        when(addressCustomerMapper.addressCustomerEntityToAddressCustomerDto(addressCustomers.get(0))).thenReturn(addressCustomerDto);

        //Act
        List<AddressCustomerDto> response = addressCustomerService.getAllAddressCustomer();

        //Assert
        verify(addressCustomerRepository).findAll();
        assertAll(
                () -> assertThat(response.size(), is(1))
        );
    }

    @Test
    void createAddressCustomerIsSuccessful() {
        //Arrange
        List<AddressCustomer> list = new ArrayList<>();
        when(addressRepository.getReferenceById(addressCustomerDto.getIdAddress())).thenReturn(address);
        when(customerRepository.getReferenceById(addressCustomerDto.getIdCustomer())).thenReturn(customer);
        when(addressCustomerRepository.findByCustomerAndAddress(customer, address)).thenReturn(null);
        when(addressCustomerRepository.findByCustomer(customer)).thenReturn(list);
        when(addressCustomerMapper.addressCustomerDtoToAddressCustomerEntity(addressCustomerDto, address, customer)).thenReturn(addressCustomer);
        when(addressCustomerRepository.save(addressCustomer)).thenReturn(addressCustomer);

        //Act
        AddressCustomer response = addressCustomerService.createAddressCustomer(addressCustomerDto);

        //Assert
        verify(addressRepository).getReferenceById(addressCustomerDto.getIdAddress());
        verify(customerRepository).getReferenceById(addressCustomerDto.getIdCustomer());
        verify(addressCustomerRepository).findByCustomerAndAddress(customer, address);
        verify(addressCustomerRepository).findByCustomer(customer);
        verify(addressCustomerMapper).addressCustomerDtoToAddressCustomerEntity(addressCustomerDto, address, customer);
        verify(addressCustomerRepository).save(addressCustomer);
    }

    @Test
    void createAddressCustomerAlreadyExists() {

        //Act
        when(addressRepository.getReferenceById(addressCustomerDto.getIdAddress())).thenReturn(address);
        when(customerRepository.getReferenceById(addressCustomerDto.getIdCustomer())).thenReturn(customer);
        when(addressCustomerRepository.findByCustomerAndAddress(customer, address)).thenThrow(EntityAlreadyExits.class);

        //Act
        final EntityAlreadyExits exception = Assertions.assertThrows(EntityAlreadyExits.class, () -> {
            addressCustomerService.createAddressCustomer(addressCustomerDto);
        });

        //Arrange
        verify(addressRepository).getReferenceById(addressCustomerDto.getIdAddress());
        verify(customerRepository).getReferenceById(addressCustomerDto.getIdCustomer());
        assertAll(
                () -> assertThat(exception.getClass(), is(EntityAlreadyExits.class)),
                () -> assertThat(exception.getMessage(), is("Customer already has this address"))
        );
    }

    @Test
    void updateAddressCustomerIsSuccessful() {

        //Arrange
        addressCustomerDto.setActive(true);
        when(addressRepository.getReferenceById(addressCustomerDto.getIdAddress())).thenReturn(address);
        when(customerRepository.getReferenceById(addressCustomerDto.getIdCustomer())).thenReturn(customer);
        when(addressCustomerRepository.getReferenceById(1)).thenReturn(addressCustomer);
        when(addressCustomerRepository.save(addressCustomer)).thenReturn(addressCustomer);

        //Act
        AddressCustomer response = addressCustomerService.updateAddressCustomer(addressCustomerDto, 1);

        //Assert
        verify(addressRepository).getReferenceById(addressCustomerDto.getIdAddress());
        verify(customerRepository).getReferenceById(addressCustomerDto.getIdCustomer());

        assertAll(
                () -> assertThat(response.isSelectedAddress(), is(true))
        );
    }

    @Test
    void deleteAddressCustomerByIdIsSuccessful() {
        //Arrange
        doNothing().when(addressCustomerRepository).deleteById(anyInt());

        //Act
        addressCustomerService.deleteAddressCustomerById(1);

        //Assert
        verify(addressCustomerRepository).deleteById(anyInt());
    }

    @Test
    void deleteAddressCustomerByIdIsNotSuccessful() {

        //Arrange
        doThrow(EntityNotFoundException.class).when(addressCustomerRepository).deleteById(anyInt());

        //Act
        final EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            addressCustomerService.deleteAddressCustomerById(1);
        });

        //Assert
        verify(addressCustomerRepository).deleteById(anyInt());
        assertAll(
                () -> assertThat(exception.getClass(), is(EntityNotFoundException.class)),
                () -> assertThat(exception.getMessage(), is("Couldn't find an addressCustomer with the given id"))
        );
    }
}
