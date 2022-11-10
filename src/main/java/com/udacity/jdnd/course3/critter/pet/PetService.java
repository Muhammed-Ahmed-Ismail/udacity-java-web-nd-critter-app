package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.User;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;

    public Pet createPet(Pet pet, Long ownerId) {
        Customer customer = customerRepository.getOne(ownerId);
        pet.setOwner(customer);
        Pet savedPet = petRepository.save(pet);
        List<Pet> ownerPets = customer.getPets();
        if(ownerPets == null)
        {
            ownerPets = new ArrayList<>();
        }
        ownerPets.add(savedPet);
        customer.setPets(ownerPets);
        return savedPet;
//        User user = customerRepository.getOne(pet.getOwner().getId());
    }

    public List<Pet> getOwnersPets(Long ownerId)
    {
        return petRepository.getPetByOwnerId(ownerId);
    }
    public Pet getPet(Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Pet> optionalPet = petRepository.findById(id);
        return optionalPet.orElseThrow(ChangeSetPersister.NotFoundException::new);
    }
}
