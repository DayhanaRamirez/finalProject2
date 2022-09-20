package com.training.program.finalproject2.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "customer")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstName")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(name = "lastName")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Column(name = "email", unique = true)
    @Email(message = "The format of the email address isn't correct")
    @NotBlank(message = "Email is required")
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPaymentMethod> userPaymentMethods;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressCustomer> addressCustomers;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Checkout> checkouts;
}
