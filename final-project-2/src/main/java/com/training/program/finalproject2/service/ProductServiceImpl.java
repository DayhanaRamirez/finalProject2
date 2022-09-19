package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.AddressDto;
import com.training.program.finalproject2.dto.ProductDto;
import com.training.program.finalproject2.entity.Address;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.entity.Product;
import com.training.program.finalproject2.exception.AddressAlreadyExistsException;
import com.training.program.finalproject2.exception.CreateUserEmailException;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.exception.ProductNameException;
import com.training.program.finalproject2.mapper.ProductMapper;
import com.training.program.finalproject2.repository.ProductRepository;
import com.training.program.finalproject2.service.interfaces.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto getProductById(int id) throws NotFoundException {
        try{
            return productMapper.productEntityToProductDto(productRepository.getReferenceById(id));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Couldn't find a product with the given id");
        }
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : products) {
            productDtoList.add(productMapper.productEntityToProductDto(product));
        }
        return productDtoList;
    }

    @Override
    public Product createProduct(ProductDto productDto) throws ProductNameException {
        Product product = productMapper.productDtoToProductEntity(productDto);
        if (productRepository.findProductByName(productDto.getName()) != null){
            throw new ProductNameException("Product already exists");
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(ProductDto productDto, int id) throws ProductNameException {
        try {
            Product product = productRepository.getReferenceById(id);
            if(productRepository.findProductByName(productDto.getName()) != null){
                throw new ProductNameException("Product already exists");
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());

            return productRepository.save(product);

        }catch (EntityNotFoundException e){
            throw new NotFoundException("Couldn't find a product with the given id");
        }
    }

    @Override
    public void deleteProductById(int id) throws NotFoundException {
        try{
            productRepository.deleteById(id);
        }catch (Exception e){
            throw new NotFoundException("Couldn't find a product with the given id");
        }
    }
}
