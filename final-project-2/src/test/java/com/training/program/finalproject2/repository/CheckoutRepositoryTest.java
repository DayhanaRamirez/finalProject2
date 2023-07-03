package com.training.program.finalproject2.repository;

import com.training.program.finalproject2.entity.CardType;
import com.training.program.finalproject2.entity.Checkout;
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
class CheckoutRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    CheckoutRepository checkoutRepository;

    private Checkout checkout1;

    private Checkout checkout2;

    @BeforeEach
    public void setUp(){
        this.checkout1 = Checkout.builder().date("01-06-1996").build();

        this.checkout2 = Checkout.builder().date("02-06-1996").build();
    }

    @Test
    public void findAllCheckouts(){
        entityManager.persist(checkout1);
        entityManager.persist(checkout2);

        List<Checkout> checkouts = checkoutRepository.findAll();
        assertThat(checkouts, hasSize(2));
    }

    @Test
    public void findCheckout(){
        entityManager.persist(checkout1);

        Checkout response = checkoutRepository.getReferenceById(checkout1.getId());
        assertThat(response.getDate(), is("01-06-1996"));
    }

    @Test
    public void createCheckout(){
        checkoutRepository.save(checkout1);
        assertThat(checkout1.getId(), notNullValue());
    }

    @Test
    public void updateCheckout(){
        entityManager.persist(checkout1);
        Checkout response = checkoutRepository.getReferenceById(checkout1.getId());
        response.setDate("02-01-1999");
        checkoutRepository.save(response);

        assertAll(
                () -> assertThat(checkout1.getDate(),is("02-01-1999"))
        );
    }

    @Test
    public void deleteCheckout(){
        Checkout response = checkoutRepository.save(checkout1);
        checkoutRepository.delete(response);
        Optional<Checkout> optionalCheckout = checkoutRepository.findById(response.getId());
        org.assertj.core.api.Assertions.assertThat(optionalCheckout.isEmpty()).isTrue();

    }


}