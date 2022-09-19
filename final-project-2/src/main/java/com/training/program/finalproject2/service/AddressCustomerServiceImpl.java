package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.AddressCustomerDto;
import com.training.program.finalproject2.entity.Address;
import com.training.program.finalproject2.entity.AddressCustomer;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.mapper.AddressCustomerMapper;
import com.training.program.finalproject2.repository.AddressCustomerRepository;
import com.training.program.finalproject2.repository.AddressRepository;
import com.training.program.finalproject2.repository.CustomerRepository;
import com.training.program.finalproject2.service.interfaces.AddressCustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class AddressCustomerServiceImpl implements AddressCustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final AddressCustomerRepository addressCustomerRepository;
    private final AddressCustomerMapper addressCustomerMapper;

    @Override
    public AddressCustomerDto getAddressCustomerById(int id) throws NotFoundException {
        try{
            return addressCustomerMapper.addressCustomerEntityToAddressCustomerDto(addressCustomerRepository.getReferenceById(id));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Couldn't find an addressCustomer with the given id");
        }
    }

    @Override
    public List<AddressCustomerDto> getAllAddressCustomer() {
        List<AddressCustomer> addressCustomerList = addressCustomerRepository.findAll();
        List<AddressCustomerDto> addressCustomerDtoList = new ArrayList<>();
        for (AddressCustomer addressCustomer : addressCustomerList) {
            addressCustomerDtoList.add(addressCustomerMapper.addressCustomerEntityToAddressCustomerDto(addressCustomer));
        }
        return addressCustomerDtoList;
    }

    @Override
    public AddressCustomer createAddressCustomer(AddressCustomerDto addressCustomerDto) throws NotFoundException {

        try{
            Address address = addressRepository.getReferenceById(addressCustomerDto.getIdAddress());
            Customer customer = customerRepository.getReferenceById(addressCustomerDto.getIdCustomer());

            AddressCustomer addressCustomer = addressCustomerMapper.addressCustomerDtoToAddressCustomerEntity(addressCustomerDto, address, customer);
            return addressCustomerRepository.save(addressCustomer);
        } catch (EntityNotFoundException e){
            throw new NotFoundException("Couldn't find an address or customer with the given IDs");
        }
    }

    @Override
    public AddressCustomer updateAddressCustomer(AddressCustomerDto addressCustomerDto, int id) throws NotFoundException {
        try{
            Address address = addressRepository.getReferenceById(addressCustomerDto.getIdAddress());
            Customer customer = customerRepository.getReferenceById(addressCustomerDto.getIdCustomer());
            AddressCustomer addressCustomer = addressCustomerRepository.getReferenceById(id);
            addressCustomer.setActive(addressCustomerDto.isActive());
            addressCustomer.setCustomer(customer);
            addressCustomer.setAddress(address);

            return addressCustomerRepository.save(addressCustomer);
        } catch (EntityNotFoundException e){
            throw new NotFoundException("Couldn't find an address or customer with the given IDs");
        }
    }

    @Override
    public void deleteAddressCustomerById(int id) throws NotFoundException {
        try{
            addressCustomerRepository.deleteById(id);
        }catch (Exception e){
            throw new NotFoundException("Couldn't find an addressCustomer with the given id");
        }
    }

}
