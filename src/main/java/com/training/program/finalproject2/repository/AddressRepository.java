package com.training.program.finalproject2.repository;

import com.training.program.finalproject2.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    boolean existsByStreetAndCityAndStateAndCountry(String street, String city, String state, String country);
}
