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
public class ProductQuantityDto implements Serializable {
    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "idProduct is required")
    private Integer idProduct;

    @NotNull(message = "idQuantity is required")
    private Integer idCheckout;
}
