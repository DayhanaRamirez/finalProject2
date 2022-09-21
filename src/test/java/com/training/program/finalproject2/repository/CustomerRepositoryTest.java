package com.training.program.finalproject2.repository;

import com.training.program.finalproject2.entity.CardType;
import com.training.program.finalproject2.entity.Customer;
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
class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer1;

    private Customer customer2;

    @BeforeEach
    public void setUp(){
        this.customer1 = Customer.builder()
                .firstName("Dayhana")
                .lastName("Ramirez")
                .email("dayhana@gmail.com")
                .build();

        this.customer2 = Customer.builder().
                firstName("Daniel")
                .lastName("Lopez")
                .email("daniel@gmail.com")
                .build();
    }

    @Test
    public void findAllCustomers(){
        entityManager.persist(customer1);
        entityManager.persist(customer2);

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers, hasSize(2));
    }

    @Test
    public void findCustomer(){
        entityManager.persist(customer1);

        Customer response = customerRepository.getReferenceById(customer1.getId());
        assertThat(response.getFirstName(), is("Dayhana"));
        assertThat(response.getLastName(), is("Ramirez"));
        assertThat(response.getEmail(), is("dayhana@gmail.com"));
    }

    @Test
    public void createCardType(){
        customerRepository.save(customer1);
        assertThat(customer1.getId(), notNullValue());
    }

    @Test
    public void updateCardType(){
        entityManager.persist(customer1);
        Customer response = customerRepository.getReferenceById(customer1.getId());
        response.setFirstName("Marcela");
        response.setLastName("Cardona");
        response.setEmail("marcela@gmail.com");
        customerRepository.save(response);

        Customer savedResponse = customerRepository.getReferenceById(customer1.getId());

        assertAll(
                () -> assertThat(savedResponse.getEmail(), is("marcela@gmail.com")),
                () -> assertThat(savedResponse.getFirstName(), is("Marcela")),
                () -> assertThat(savedResponse.getLastName(), is("Cardona"))
        );
    }

    @Test
    public void deleteCardType(){
        Customer response = customerRepository.save(customer1);
        customerRepository.delete(response);
        Optional<Customer> optionalCustomer = customerRepository.findById(response.getId());
        org.assertj.core.api.Assertions.assertThat(optionalCustomer.isEmpty()).isTrue();

    }


}