package com.training.program.finalproject2.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckOutRequest {
    @NotNull(message = "IdCustomer cannot be null")
    private Integer idCustomer;

    @NotNull(message = "IdProduct cannot be null")
    private Integer idProduct;

    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;
}
