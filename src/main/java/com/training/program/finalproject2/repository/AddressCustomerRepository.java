package com.training.program.finalproject2.repository;

import com.training.program.finalproject2.entity.Address;
import com.training.program.finalproject2.entity.AddressCustomer;
import com.training.program.finalproject2.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressCustomerRepository extends JpaRepository<AddressCustomer, Integer> {
    AddressCustomer findBySelectedAddressTrue();
    List<AddressCustomer> findByCustomer(Customer customer);
    AddressCustomer findByCustomerAndAddress(Customer customer, Address address);
}
