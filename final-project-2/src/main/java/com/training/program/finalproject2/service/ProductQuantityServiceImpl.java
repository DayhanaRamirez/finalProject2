package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.ProductDto;
import com.training.program.finalproject2.dto.ProductQuantityDto;
import com.training.program.finalproject2.entity.Checkout;
import com.training.program.finalproject2.entity.Product;
import com.training.program.finalproject2.entity.ProductQuantity;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.mapper.ProductQuantityMapper;
import com.training.program.finalproject2.repository.CheckoutRepository;
import com.training.program.finalproject2.repository.ProductQuantityRepository;
import com.training.program.finalproject2.repository.ProductRepository;
import com.training.program.finalproject2.service.interfaces.ProductQuantityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ProductQuantityServiceImpl implements ProductQuantityService {

    private final ProductQuantityRepository productQuantityRepository;
    private final ProductQuantityMapper productQuantityMapper;

    private final ProductRepository productRepository;

    private final CheckoutRepository checkoutRepository;

    @Override
    public ProductQuantityDto getProductQuantityById(int id) throws NotFoundException {
        try{
            return productQuantityMapper.productQuantityEntityToDto(productQuantityRepository.getReferenceById(id));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Couldn't find a productQuantity with the given id");
        }
    }

    @Override
    public List<ProductQuantityDto> getAllProductQuantity() {
        List<ProductQuantity> productQuantityList = productQuantityRepository.findAll();
        List<ProductQuantityDto> productQuantityDtoList = new ArrayList<>();
        for (ProductQuantity productQuantity : productQuantityList) {
            productQuantityDtoList.add(productQuantityMapper.productQuantityEntityToDto(productQuantity));
        }
        return productQuantityDtoList;
    }

    @Override
    public ProductQuantity createProductQuantity(ProductQuantityDto productQuantityDto) throws NotFoundException {
        try{
            Product product = productRepository.getReferenceById(productQuantityDto.getIdProduct());
            Checkout checkout = checkoutRepository.getReferenceById(productQuantityDto.getIdCheckout());

            ProductQuantity productQuantity = productQuantityMapper.productQuantityDtoToEntity(productQuantityDto, product, checkout);
            return productQuantityRepository.save(productQuantity);
        }catch (EntityNotFoundException e){
            throw new NotFoundException("Couldn't find a productQuantity with the given IDs");
        }
    }

    @Override
    public ProductQuantity updateProductQuantity(ProductQuantityDto productQuantityDto, int id) throws NotFoundException {
        try{
            Product product = productRepository.getReferenceById(productQuantityDto.getIdProduct());
            Checkout checkout = checkoutRepository.getReferenceById(productQuantityDto.getIdCheckout());
            ProductQuantity productQuantity = productQuantityRepository.getReferenceById(id);
            productQuantity.setQuantity(productQuantityDto.getQuantity());
            productQuantity.setProduct(product);
            productQuantity.setCheckout(checkout);
            return productQuantityRepository.save(productQuantity);
        } catch (EntityNotFoundException e){
            throw new NotFoundException("Couldn't find a productQuantity with the given id");
        }
    }

    @Override
    public void deleteProductQuantityById(int id) throws NotFoundException {
        try{
            productQuantityRepository.deleteById(id);
        }catch (Exception e){
            throw new NotFoundException("Couldn't find a productQuantity  with the given id");
        }

    }
}
