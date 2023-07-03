package com.training.program.finalproject2.entity;

import com.training.program.finalproject2.dto.CheckoutDto;
import com.training.program.finalproject2.dto.CustomerDto;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.mapper.CheckoutMapper;
import com.training.program.finalproject2.repository.CheckoutRepository;
import com.training.program.finalproject2.repository.CustomerRepository;
import com.training.program.finalproject2.service.CheckoutServiceImpl;
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

class CheckoutTest {
    @Mock
    private CheckoutRepository checkoutRepository;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CheckoutMapper checkoutMapper;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    private Checkout checkout;

    private CheckoutDto checkoutDto;

    private Customer customer;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.checkout = Checkout.builder()
                .id(1)
                .date("01-06-1996").build();

        this.checkoutDto = CheckoutDto.builder()
                .date("01-06-1996")
                .idCustomer(1)
                .build();

        this.customer = Customer.builder()
                .email("dayha@gmail.com")
                .lastName("Ramirez")
                .firstName("Dayhana")
                .id(1).build();
    }

    @Test
    void getCheckoutByIdIsSuccessful() {

        //Arrange
        when(checkoutRepository.getReferenceById(anyInt())).thenReturn(checkout);
        when(checkoutMapper.checkoutEntityToCheckoutDto(any(Checkout.class))).thenReturn(checkoutDto);

        //Act
        CheckoutDto response = checkoutService.getCheckoutById(1);

        //Assert
        verify(checkoutRepository).getReferenceById(anyInt());
        verify(checkoutMapper).checkoutEntityToCheckoutDto(any(Checkout.class));
    }

    @Test
    void getCheckoutByIdDoesNotExist(){

        //Arrange
        when(checkoutRepository.getReferenceById(anyInt())).thenThrow(EntityNotFoundException.class);

        //Act
        final NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            checkoutService.getCheckoutById(anyInt());
        });

        //Assert
        verify(checkoutRepository).getReferenceById(anyInt());
        verify(checkoutMapper, times(0)).checkoutEntityToCheckoutDto(any(Checkout.class));
        assertAll(
                () -> assertThat(exception.getClass(), is(NotFoundException.class)),
                () -> assertThat(exception.getMessage(), is("Couldn't find a checkout with the given id"))
        );
    }

    @Test
    void getAllCheckouts() {

        //Arrange
        List<Checkout> checkouts = new ArrayList<>();
        checkouts.add(checkout);

        when(checkoutRepository.findAll()).thenReturn(checkouts);
        when(checkoutMapper.checkoutEntityToCheckoutDto(checkouts.get(0))).thenReturn(checkoutDto);

        //Act
        List<CheckoutDto> response = checkoutService.getAllCheckouts();

        //Assert
        verify(checkoutRepository).findAll();
        verify(checkoutMapper).checkoutEntityToCheckoutDto(any(Checkout.class));
        assertAll(
                () -> assertThat(response.size(), is(1))
        );
    }

    @Test
    void createCheckoutIsSuccessful() {

        //Act
        when(customerRepository.getReferenceById(any(Integer.class))).thenReturn(customer);
        when(checkoutMapper.checkoutDtoToCheckoutEntity(checkoutDto, customer)).thenReturn(checkout);
        when(checkoutRepository.save(any(Checkout.class))).thenReturn(checkout);

        //Act
        Checkout response = checkoutService.createCheckout(checkoutDto);

        //Arrange
        verify(checkoutRepository).save(any(Checkout.class));
        verify(checkoutMapper).checkoutDtoToCheckoutEntity(any(CheckoutDto.class), any(Customer.class));
    }

    @Test
    void updateCheckoutIsSuccessful() {

        //Arrange
        checkoutDto.setDate("other date");
        when(customerRepository.getReferenceById(
                eq(checkoutDto.getIdCustomer())))
                .thenReturn(customer);
        when(checkoutRepository.getReferenceById(
                anyInt()))
                .thenReturn(checkout);
        when(checkoutRepository.save(any(Checkout.class))).thenReturn(checkout);

        //Act
        Checkout response = checkoutService.updateCheckout(checkoutDto, 1);

        //Assert
        verify(customerRepository).getReferenceById(
                eq(checkoutDto.getIdCustomer()));
        verify(checkoutRepository).getReferenceById(
                anyInt());
        verify(checkoutRepository).save(
                any(Checkout.class));
        assertAll(
                () -> assertThat(response.getDate(), is("other date"))
        );
    }

    @Test
    void updateCheckoutIsNotSuccessful() {

        //Arrange
        when(customerRepository.getReferenceById(anyInt())).thenThrow(EntityNotFoundException.class);

        //Act
        final EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            checkoutService.updateCheckout(checkoutDto, 1);
        });

        //Assert
        assertAll(
                () -> assertThat(exception.getClass(), is(EntityNotFoundException.class)),
                () -> assertThat(exception.getMessage(), is("Couldn't find a user or checkout with the given id"))
        );
    }

    @Test
    void deleteCheckoutByIdIsSuccessful() {
        //Arrange
        doNothing().when(checkoutRepository).deleteById(anyInt());

        //Act
        checkoutService.deleteCheckoutById(1);

        //Assert
        verify(checkoutRepository).deleteById(anyInt());
    }

    @Test
    void deleteCheckoutByIdIsNotSuccessful() {
        //Arrange
        doThrow(EntityNotFoundException.class).when(checkoutRepository).deleteById(anyInt());

        //Act
        final EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            checkoutService.deleteCheckoutById(1);
        });

        //Assert
        verify(checkoutRepository).deleteById(anyInt());
        assertAll(
                () -> assertThat(exception.getClass(), is(EntityNotFoundException.class)),
                () -> assertThat(exception.getMessage(), is("Couldn't find a checkout with the given id"))
        );
    }
}