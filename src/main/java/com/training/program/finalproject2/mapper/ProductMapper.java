package com.training.program.finalproject2.mapper;

import com.training.program.finalproject2.dto.ProductDto;
import com.training.program.finalproject2.entity.Product;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ProductMapper {
    public Product productDtoToProductEntity(ProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .build();
    }

    public ProductDto productEntityToProductDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
