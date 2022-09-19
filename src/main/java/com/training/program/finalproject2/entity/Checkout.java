package com.training.program.finalproject2.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "checkout")
public class Checkout implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    @NotBlank(message = "Date is required")
    private String date;

    @ManyToOne
    @JoinColumn(name = "fk_customer")
    private Customer customer;

    @OneToMany(mappedBy = "checkout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductQuantity> productQuantities;

    @OneToOne(mappedBy = "checkout", cascade = CascadeType.ALL, orphanRemoval = true)
    private MyOrder order;

}
