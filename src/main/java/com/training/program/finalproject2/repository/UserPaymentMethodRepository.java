package com.training.program.finalproject2.repository;

import com.training.program.finalproject2.entity.CardType;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.entity.PaymentMethod;
import com.training.program.finalproject2.entity.UserPaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPaymentMethodRepository extends JpaRepository<UserPaymentMethod, Integer> {
    UserPaymentMethod findBySelectedPaymentMethodTrue();

    List<UserPaymentMethod> findByCustomer(Customer customer);

    UserPaymentMethod findByCardTypeAndCustomerAndPaymentMethodAndCardNumber(CardType cardType, Customer customer, PaymentMethod paymentMethod, String cardNumber);
}
