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
public class MyOrderDto implements Serializable {
    @NotBlank(message = "Date is required")
    private String date;

    @NotNull(message = "idCheckout is required")
    private Integer idCheckout;
}
