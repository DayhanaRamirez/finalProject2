package com.training.program.finalproject2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPaymentMethodDto implements Serializable {

    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotNull(message = "isActive number is required")
    private boolean isActive;

    @NotNull(message = "idCardType number is required")
    private Integer idCardType;

    @NotNull(message = "idCustomer is required")
    private Integer idCustomer;

    @NotNull(message = "idPaymentMethod is required")
    private Integer idPaymentMethod;
}
