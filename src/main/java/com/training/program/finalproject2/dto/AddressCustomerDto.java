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
public class AddressCustomerDto implements Serializable {

    @NotNull(message = "Active is required")
    private boolean active;

    @NotNull(message = "IdCustomer is required")
    private Integer idCustomer;

    @NotNull(message = "IdAddress is required")
    private Integer idAddress;
}
