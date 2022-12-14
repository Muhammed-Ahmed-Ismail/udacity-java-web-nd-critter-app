package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PetRepository petRepository;

    public Customer createCustomer(Customer customer)
    {
        return customerRepository.save(customer);
    }
    public Customer findCustomerByPetId(Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Pet> optionalPet = petRepository.findById(id);
        Pet pet = optionalPet.orElseThrow(ChangeSetPersister.NotFoundException::new);
        return customerRepository.findByPetsId(id);
    }
    public List<Customer> getAllCustomers(){
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }
}
