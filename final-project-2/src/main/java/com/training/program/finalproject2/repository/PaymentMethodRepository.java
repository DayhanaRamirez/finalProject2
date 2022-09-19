package com.training.program.finalproject2.repository;

import com.training.program.finalproject2.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository <PaymentMethod, Integer> {
    public PaymentMethod findPaymentMethodByType(String type);
}
