package com.training.program.finalproject2.repository;

import com.training.program.finalproject2.entity.CardType;
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
class CardTypeRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    CardTypeRepository cardTypeRepository;

    private CardType cardType1;

    private CardType cardType2;

    @BeforeEach
    public void setUp(){
        this.cardType1 = CardType.builder().type("Credit card")
                .build();
        this.cardType2 = CardType.builder().type("Debit card")
                .build();
    }

    @Test
    public void findAllCardTypes(){
        entityManager.persist(cardType1);
        entityManager.persist(cardType2);

        List<CardType> cardTypeList = cardTypeRepository.findAll();
        assertThat(cardTypeList, hasSize(2));
    }

    @Test
    public void findCardType(){
        entityManager.persist(cardType1);

        CardType response = cardTypeRepository.getReferenceById(cardType1.getId());
        assertThat(response.getType(), is("Credit card"));
    }

    @Test
    public void createCardType(){
        cardTypeRepository.save(cardType1);
        assertThat(cardType1.getId(), notNullValue());
    }

    @Test
    public void updateCardType(){
        entityManager.persist(cardType1);
        CardType response = cardTypeRepository.getReferenceById(cardType1.getId());
        response.setType("Cash");
        cardTypeRepository.save(response);

        assertAll(
                () -> assertThat(cardType1.getType(), is("Cash"))
        );
    }

    @Test
    public void deleteCardType(){
        CardType response = cardTypeRepository.save(cardType1);
        cardTypeRepository.delete(response);
        Optional<CardType> optionalCardType = cardTypeRepository.findById(response.getId());
        org.assertj.core.api.Assertions.assertThat(optionalCardType.isEmpty()).isTrue();

    }


}