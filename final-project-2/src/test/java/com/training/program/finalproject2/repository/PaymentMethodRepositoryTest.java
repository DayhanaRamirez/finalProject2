package com.training.program.finalproject2.repository;


import com.training.program.finalproject2.entity.CardType;
import com.training.program.finalproject2.entity.PaymentMethod;
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
class PaymentMethodRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    private PaymentMethod paymentMethod1;

    private PaymentMethod paymentMethod2;

    @BeforeEach
    public void setUp(){
        this.paymentMethod1 = PaymentMethod.builder().type("Visa")
                .build();
        this.paymentMethod2 = PaymentMethod.builder().type("Mastercard")
                .build();
    }

    @Test
    public void findAllPaymentMethods(){
        entityManager.persist(paymentMethod1);
        entityManager.persist(paymentMethod2);

        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList, hasSize(2));
    }

    @Test
    public void findPaymentMethods(){
        entityManager.persist(paymentMethod1);

        PaymentMethod response = paymentMethodRepository.getReferenceById(paymentMethod1.getId());
        assertThat(response.getType(), is("Visa"));
    }

    @Test
    public void createPaymentMethod(){
        paymentMethodRepository.save(paymentMethod1);
        assertThat(paymentMethod1.getId(), notNullValue());
    }

    @Test
    public void updatePaymentMethod(){
        entityManager.persist(paymentMethod1);
        PaymentMethod response = paymentMethodRepository.getReferenceById(paymentMethod1.getId());
        response.setType("Mastercard");
        paymentMethodRepository.save(response);
        PaymentMethod savedResponse = paymentMethodRepository.getReferenceById(paymentMethod1.getId());

        assertAll(
                () -> assertThat(savedResponse.getType(), is("Mastercard"))
        );
    }

    @Test
    public void deleteCardType(){
        PaymentMethod response = paymentMethodRepository.save(paymentMethod1);
        paymentMethodRepository.delete(response);
        Optional<PaymentMethod> optionalPaymentMethod = paymentMethodRepository.findById(response.getId());
        org.assertj.core.api.Assertions.assertThat(optionalPaymentMethod.isEmpty()).isTrue();
    }
}