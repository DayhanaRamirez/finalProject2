package com.training.program.finalproject2.repository;

import com.training.program.finalproject2.dto.AddressDto;
import com.training.program.finalproject2.entity.Address;
import com.training.program.finalproject2.entity.AddressCustomer;
import com.training.program.finalproject2.entity.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class AddressCustomerRepositoryTest {

    @Autowired
    private  AddressCustomerRepository addressCustomerRepository;
    private Address address;
    private Customer customer;
    private AddressCustomer addressCustomer;

    @BeforeEach
    void setUp(){
        this.address = Address.builder()
                .street("Calle 2")
                .city("Medellin")
                .state("Antioquia")
                .country("Colombia").build();

        this.customer = Customer.builder()
                .firstName("Dayhana")
                .lastName("Ramirez")
                .email("dayhana@gmail.com")
                .build();

        this.addressCustomer = AddressCustomer.builder()
                .selectedAddress(true)
                .address(address)
                .customer(customer)
                .build();
    }

    @Test
    public void saveAddressCustomer(){
        AddressCustomer response = addressCustomerRepository.save(addressCustomer);
        assertAll(
                () -> assertThat(response.getId(), notNullValue()),
                () -> assertThat(response.getAddress(), notNullValue()),
                () -> assertThat(response.getCustomer(), notNullValue())
        );
    }

    @Test
    public void updateAddressCustomer(){
        AddressCustomer response = addressCustomerRepository.save(addressCustomer);

        Address address1 = Address.builder()
                .id(2)
                .street("Calle 2")
                .city("Medellin")
                .state("Antioquia")
                .country("Colombia").build();

        Customer customer1 = Customer.builder()
                .id(2)
                .firstName("Daniel")
                .lastName("Lopez")
                .email("daniel@gmail.com").
                build();

        response.setAddress(address1);
        response.setCustomer(customer1);

        AddressCustomer saved = addressCustomerRepository.save(response);

        assertAll(
                () -> assertThat(saved.getId(), notNullValue()),
                () -> assertThat(saved.getAddress(), notNullValue()),
                () -> assertThat(saved.getCustomer(), notNullValue())
        );
    }

    @Test
    public void deleteAddressCustomer(){
        AddressCustomer savedAddressCustomer  = addressCustomerRepository.save(addressCustomer);
        addressCustomerRepository.delete(addressCustomer);
        Optional<AddressCustomer> optionalAddressCustomer = addressCustomerRepository.findById(savedAddressCustomer.getId());
        Assertions.assertThat(optionalAddressCustomer.isEmpty()).isTrue();
    }

    @Test
    public void getAddressCustomer(){
        AddressCustomer addressCustomer = addressCustomerRepository.getReferenceById(1);
        Assertions.assertThat(addressCustomer.getId()).isEqualTo(1);
    }

}