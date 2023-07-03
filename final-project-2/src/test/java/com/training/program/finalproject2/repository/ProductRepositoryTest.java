package com.training.program.finalproject2.repository;


import com.training.program.finalproject2.entity.PaymentMethod;
import com.training.program.finalproject2.entity.Product;
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
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    private Product product1;

    private Product product2;

    @BeforeEach()
    public void setUp(){
        this.product1 = Product.builder()
                .name("Chocolate")
                .description("Rico")
                .price(20D)
                .build();

        this.product2 = Product.builder()
                .name("Caramelo")
                .description("Mas rico")
                .price(30D)
                .build();
    }


    @Test
    public void findAllProduct(){
        entityManager.persist(product1);
        entityManager.persist(product2);

        List<Product> productList = productRepository.findAll();
        assertThat(productList, hasSize(2));
    }


    @Test
    public void findProduct(){
        entityManager.persist(product1);

        Product response = productRepository.getReferenceById(product1.getId());
        assertThat(response.getName(), is("Chocolate"));
        assertThat(response.getDescription(), is("Rico"));
        assertThat(response.getPrice(), is(20D));
    }

    @Test
    public void createProduct(){
        productRepository.save(product1);
        assertThat(product1.getId(), notNullValue());
    }

    @Test
    public void updateProduct(){
        entityManager.persist(product1);
        Product response = productRepository.getReferenceById(product1.getId());
        response.setName("Banano");
        response.setDescription("Fruta");
        response.setPrice(30D);
        productRepository.save(response);
        Product savedResponse = productRepository.getReferenceById(product1.getId());

        assertAll(
                () -> assertThat(savedResponse.getName(), is("Banano")),
                () -> assertThat(savedResponse.getPrice(), is(30D)),
                () -> assertThat(savedResponse.getDescription(), is("Fruta"))
        );
    }

    @Test
    public void deleteProduct(){
        Product response = productRepository.save(product1);
        productRepository.delete(response);
        Optional<Product> optionalProduct = productRepository.findById(response.getId());
        org.assertj.core.api.Assertions.assertThat(optionalProduct.isEmpty()).isTrue();
    }




}