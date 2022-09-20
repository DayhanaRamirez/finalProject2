package com.training.program.finalproject2.repository;

import com.training.program.finalproject2.entity.Checkout;
import com.training.program.finalproject2.entity.Product;
import com.training.program.finalproject2.entity.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, Integer> {

    ProductQuantity findByProductAndCheckout(Product product, Checkout checkout);

    List<ProductQuantity> findByCheckout(Checkout checkout);
}
