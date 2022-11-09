package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.User;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;

    public Pet createPet(Pet pet, Long ownerId) {
        Customer customer = customerRepository.getOne(ownerId);
        pet.setOwner(customer);
        return petRepository.save(pet);
//        User user = customerRepository.getOne(pet.getOwner().getId());
    }

    public List<Pet> getOwnersPets(Long ownerId)
    {
        return petRepository.getPetByOwnerId(ownerId);
    }
}
