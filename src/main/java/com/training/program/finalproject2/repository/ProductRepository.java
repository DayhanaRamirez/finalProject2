package com.training.program.finalproject2.repository;

import com.training.program.finalproject2.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    public Product findProductByName(String name);
}
