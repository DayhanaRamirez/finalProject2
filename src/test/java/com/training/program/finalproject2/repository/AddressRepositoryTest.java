package com.training.program.finalproject2.repository;

import com.training.program.finalproject2.entity.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    AddressRepository addressRepository;

    @Test
    public void findAllAddress(){
        Address address1 = Address.builder()
                .street("Calle 2")
                .state("Antioquia")
                .city("Medellin")
                .country("Colombia")
                .build();

        Address address2 = Address.builder()
                .street("Calle 1")
                .state("Antioquia")
                .city("Medellin")
                .country("Colombia")
                .build();

        entityManager.persist(address1);
        entityManager.persist(address2);

        List<Address> addresses = addressRepository.findAll();
        assertThat(addresses, hasSize(2));
    }

    @Test
    public void findAddress(){
        Address address1 = Address.builder()
                .street("Calle 2")
                .state("Antioquia")
                .city("Medellin")
                .country("Colombia")
                .build();

        entityManager.persist(address1);

        Address response = addressRepository.getReferenceById(address1.getId());
        assertThat(response.getCity(), is("Medellin"));
        assertThat(response.getStreet(), is("Calle 2"));
        assertThat(response.getState(), is("Antioquia"));
        assertThat(response.getCountry(), is("Colombia"));
    }

    @Test
    public void createAddress(){
        Address address1 = Address.builder()
                .street("Calle 2")
                .state("Antioquia")
                .city("Medellin")
                .country("Colombia")
                .build();

        addressRepository.save(address1);
        assertThat(address1.getId(), notNullValue());
    }

    @Test
    public void updateAddress(){
        Address address1 = Address.builder()
                .street("Calle 2")
                .state("Antioquia")
                .city("Medellin")
                .country("Colombia")
                .build();

       entityManager.persist(address1);

       Address response = addressRepository.getReferenceById(address1.getId());
       response.setStreet("Calle 1");
       response.setCity("Bogota");
       response.setState("Cundinamarca");
       addressRepository.save(response);

        assertAll(
                () -> assertThat(address1.getStreet(), is("Calle 1")),
                () -> assertThat(address1.getCity(), is("Bogota")),
                () -> assertThat(address1.getState(), is("Cundinamarca"))
        );
    }

    @Test
    public void deleteAddress(){
        Address address1 = Address.builder()
                .street("Calle 2")
                .state("Antioquia")
                .city("Medellin")
                .country("Colombia")
                .build();

        Address response = addressRepository.save(address1);
        addressRepository.delete(response);
        Optional<Address> optionalAddress = addressRepository.findById(response.getId());
        org.assertj.core.api.Assertions.assertThat(optionalAddress.isEmpty()).isTrue();

    }



}