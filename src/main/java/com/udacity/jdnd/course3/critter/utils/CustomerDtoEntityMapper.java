package com.udacity.jdnd.course3.critter.utils;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerDtoEntityMapper implements DtoEntityMapper<Customer, CustomerDTO> {
    @Autowired
    private PetEntityMapper petEntityMapper;

    @Override
    public Customer fromDtoToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(dto, customer);
        return customer;
    }

    @Override
    public CustomerDTO fromEntityToDto(Customer entity) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(entity, customerDTO);
        List<Pet> pets = entity.getPets();
        if (pets != null) {
            customerDTO.setPetIds(
                    entity.getPets()
                            .stream()
                            .map(Pet::getId)
                            .collect(Collectors.toList())
            );
        }
        return customerDTO;
    }
}
