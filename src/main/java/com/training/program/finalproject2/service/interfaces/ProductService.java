package com.training.program.finalproject2.service.interfaces;

import com.training.program.finalproject2.dto.ProductDto;
import com.training.program.finalproject2.entity.Product;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.exception.ProductNameException;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDto productDto) throws ProductNameException;

    ProductDto getProductById(int id) throws NotFoundException;

    List<ProductDto> getAllProducts();

    Product updateProduct(ProductDto productDto, int id) throws ProductNameException;

    void deleteProductById(int id) throws NotFoundException;
}
