package com.training.program.finalproject2.repository;

import com.training.program.finalproject2.entity.CardType;
import com.training.program.finalproject2.entity.Checkout;
import com.training.program.finalproject2.entity.MyOrder;
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
class MyOrderRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MyOrderRepository myOrderRepository;

    private MyOrder myOrder1;

    private MyOrder myOrder2;

    @BeforeEach
    public void setUp(){
        this.myOrder1 = MyOrder.builder().date("01-06-1996")
                .build();
        this.myOrder2= MyOrder.builder().date("01-06-1998")
                .build();
    }

    @Test
    public void findAllMyOrders(){
        entityManager.persist(myOrder1);
        entityManager.persist(myOrder2);

        List<MyOrder> myOrderList = myOrderRepository.findAll();
        assertThat(myOrderList, hasSize(2));
    }

    @Test
    public void findMyOrder(){
        entityManager.persist(myOrder1);

        MyOrder response = myOrderRepository.getReferenceById(myOrder1.getId());
        assertThat(response.getDate(), is("01-06-1996"));
    }

    @Test
    public void createMyOrder(){
        myOrderRepository.save(myOrder1);
        assertThat(myOrder1.getId(), notNullValue());
    }

    @Test
    public void updateMyOrder(){
        entityManager.persist(myOrder1);
        MyOrder response = myOrderRepository.getReferenceById(myOrder1.getId());
        response.setDate("03-01-1999");
        myOrderRepository.save(response);
        MyOrder savedResponse = myOrderRepository.getReferenceById(myOrder1.getId());

        assertAll(
                () -> assertThat(savedResponse.getDate(), is("03-01-1999"))
        );
    }

    @Test
    public void deleteMyOrder(){
        MyOrder response = myOrderRepository.save(myOrder1);
        myOrderRepository.delete(response);
        Optional<MyOrder> optionalMyOrder = myOrderRepository.findById(response.getId());
        org.assertj.core.api.Assertions.assertThat(optionalMyOrder.isEmpty()).isTrue();

    }


}