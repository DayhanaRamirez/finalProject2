package com.training.program.finalproject2.service;

import com.training.program.finalproject2.dto.AddressCustomerDto;
import com.training.program.finalproject2.entity.Address;
import com.training.program.finalproject2.entity.AddressCustomer;
import com.training.program.finalproject2.entity.Customer;
import com.training.program.finalproject2.exception.EntityAlreadyExits;
import com.training.program.finalproject2.exception.NotFoundException;
import com.training.program.finalproject2.mapper.AddressCustomerMapper;
import com.training.program.finalproject2.repository.AddressCustomerRepository;
import com.training.program.finalproject2.repository.AddressRepository;
import com.training.program.finalproject2.repository.CustomerRepository;
import com.training.program.finalproject2.service.interfaces.AddressCustomerService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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

            checkEntity(address, customer);

            List<AddressCustomer> list = addressCustomerRepository.findByCustomer(customer);
            AddressCustomer addressCustomer = addressCustomerMapper.addressCustomerDtoToAddressCustomerEntity(addressCustomerDto, address, customer);

            if (list.isEmpty()){
                addressCustomer.setSelectedAddress(true);
            } else {
                addressCustomer.setSelectedAddress(false);
            }
            return addressCustomerRepository.save(addressCustomer);
        } catch (Exception e){
            if (e.getClass() == DataIntegrityViolationException.class) {
                throw new EntityNotFoundException("Couldn't find an address or customer with the given IDs");
            } else {
                throw new EntityAlreadyExits("Customer already has this address");
            }
        }
    }

    private void checkEntity(Address address, Customer customer){
        try{
            if(addressCustomerRepository.findByCustomerAndAddress(customer, address) != null) {
                throw new EntityAlreadyExits("User already has that address");
            }
        } catch (Exception e){
            throw new EntityAlreadyExits("Couldn't find an address or customer with the given IDs");
        }
    }

    @Override
    public AddressCustomer updateAddressCustomer(AddressCustomerDto addressCustomerDto, int id) throws NotFoundException {
        try{
            Address address = addressRepository.getReferenceById(addressCustomerDto.getIdAddress());
            Customer customer = customerRepository.getReferenceById(addressCustomerDto.getIdCustomer());
            AddressCustomer addressCustomer = addressCustomerRepository.getReferenceById(id);
            addressCustomer.setSelectedAddress(addressCustomerDto.isActive());
            addressCustomer.setCustomer(customer);
            addressCustomer.setAddress(address);

            return addressCustomerRepository.save(addressCustomer);
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException("Couldn't find an address or customer with the given IDs");
        }
    }

    @Override
    public void deleteAddressCustomerById(int id) throws NotFoundException {
        try{
            addressCustomerRepository.deleteById(id);
        }catch (Exception e){
            throw new EntityNotFoundException("Couldn't find an addressCustomer with the given id");
        }
    }

    @Override
    public void setActiveAddressCustomer(int id) throws NotFoundException {
        try {
            AddressCustomer oldAddress = addressCustomerRepository.findBySelectedAddressTrue();
            oldAddress.setSelectedAddress(false);

            AddressCustomer newAddress = addressCustomerRepository.getReferenceById(id);
            newAddress.setSelectedAddress(true);

            addressCustomerRepository.save(oldAddress);
            addressCustomerRepository.save(newAddress);
        } catch (Exception e){
            throw new NotFoundException("Couldn't find an addressCustomer with the given id");
        }
    }

}
