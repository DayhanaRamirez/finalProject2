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
public class ProductDto implements Serializable {
    @NotBlank(message = "First name is required")
    private String name;

    @NotBlank(message = "Description name is required")
    private String description;

    @NotNull(message = "Price is required")
    private Double price;
}
