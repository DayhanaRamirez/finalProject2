package com.training.program.finalproject2.mapper;

import com.training.program.finalproject2.dto.ProductQuantityDto;
import com.training.program.finalproject2.entity.Checkout;
import com.training.program.finalproject2.entity.Product;
import com.training.program.finalproject2.entity.ProductQuantity;
import com.training.program.finalproject2.service.interfaces.ProductQuantityService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ProductQuantityMapper {
    public ProductQuantity productQuantityDtoToEntity(ProductQuantityDto productQuantityDto, Product product, Checkout checkout){
        return ProductQuantity.builder()
                .quantity(productQuantityDto.getQuantity())
                .product(product)
                .checkout(checkout)
                .build();
    }

    public ProductQuantityDto productQuantityEntityToDto(ProductQuantity productQuantity){
        return ProductQuantityDto.builder()
                .quantity(productQuantity.getQuantity())
                .idProduct(productQuantity.getProduct().getId())
                .idCheckout(productQuantity.getCheckout().getId())
                .build();
    }

}
