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
public class CheckoutDto implements Serializable {

    @NotBlank(message = "First name is required")
    private String date;

    @NotNull(message = "idCustomer is required")
    private Integer idCustomer;

}
