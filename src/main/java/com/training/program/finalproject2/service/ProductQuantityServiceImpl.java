package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.ProductDto;
import com.training.program.finalproject2.dto.ProductQuantityDto;
import com.training.program.finalproject2.entity.Checkout;
import com.training.program.finalproject2.entity.Product;
import com.training.program.finalproject2.entity.ProductQuantity;
import com.training.program.finalproject2.exception.EntityAlreadyExits;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.mapper.ProductQuantityMapper;
import com.training.program.finalproject2.repository.CheckoutRepository;
import com.training.program.finalproject2.repository.ProductQuantityRepository;
import com.training.program.finalproject2.repository.ProductRepository;
import com.training.program.finalproject2.service.interfaces.CheckoutService;
import com.training.program.finalproject2.service.interfaces.ProductQuantityService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
    private final CheckoutService checkoutService;

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

            checkEntity(product, checkout);

            ProductQuantity productQuantity = productQuantityMapper.productQuantityDtoToEntity(productQuantityDto, product, checkout);
            return productQuantityRepository.save(productQuantity);
        }catch (Exception e) {
            throw new EntityAlreadyExits("Checkout already has this product");
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
            checkCheckout(checkoutRepository.getReferenceById(id));
        }catch (Exception e){
            throw new NotFoundException("Couldn't find a productQuantity  with the given id");
        }
    }

    @Override
    public void modifyProductQuantityById(ProductQuantityDto productQuantityDto) throws NotFoundException {
        try{
            Product product = productRepository.getReferenceById(productQuantityDto.getIdProduct());
            Checkout checkout = checkoutRepository.getReferenceById(productQuantityDto.getIdCheckout());

            ProductQuantity productQuantity = productQuantityRepository.findByProductAndCheckout(product, checkout);

            if(productQuantity == null){
                throw new EntityNotFoundException("Product does not exist in the checkout or checkout does not exist");
            }

            if(productQuantityDto.getQuantity() == 0) {
                deleteProductQuantityById(productQuantity.getId());
                checkCheckout(checkout);
                return;
            }

            productQuantity.setQuantity(productQuantityDto.getQuantity());
            productQuantityRepository.save(productQuantity);

        } catch (Exception e) {
            throw new EntityNotFoundException("Product does not exist in the checkout or checkout does not exist");
        }
    }

    private void checkCheckout(Checkout checkout) {
        List<ProductQuantity> list = productQuantityRepository.findByCheckout(checkout);
        if(list.isEmpty()){
            checkoutService.deleteCheckoutById(checkout.getId());
        }
    }

    private void checkEntity(Product product, Checkout checkout) {
        try{
            if(productQuantityRepository.findByProductAndCheckout(product, checkout) != null){
                throw new EntityAlreadyExits("Checkout already has this product");
            }
        } catch (Exception e){
            throw new EntityAlreadyExits("Checkout already has this product");
        }
    }
}
