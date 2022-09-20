package com.training.program.finalproject2.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "address")
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "street")
    @NotBlank(message = "Street is required")
    private String street;

    @Column(name = "city")
    @NotBlank(message = "City is required")
    private String city;

    @Column(name = "state")
    @NotBlank(message = "State name is required")
    private String state;

    @Column(name = "country")
    @NotBlank(message = "Country name is required")
    private String country;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressCustomer> addressCustomers;
}
