package com.training.program.finalproject2.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "myOrder")
public class MyOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    @NotBlank(message = "Date is required")
    private String date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_checkout", referencedColumnName = "id")
    private Checkout checkout;

}
