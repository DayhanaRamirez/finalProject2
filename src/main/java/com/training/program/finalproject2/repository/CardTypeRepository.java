package com.training.program.finalproject2.repository;

import com.training.program.finalproject2.entity.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardTypeRepository extends JpaRepository<CardType, Integer> {
    CardType findCardTypeByType(String type);
}
