package com.training.program.finalproject2.repository;

import com.training.program.finalproject2.entity.MyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyOrderRepository extends JpaRepository<MyOrder, Integer> {
}
