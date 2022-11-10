package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.utils.PetEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;
    @Autowired
    PetEntityMapper petEntityMapper;
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.createPet(petEntityMapper.fromDtoToEntity(petDTO),petDTO.getOwnerId());
        return petEntityMapper.fromEntityToDto(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        Pet pet = null;
        try {
            pet = petService.getPet(petId);
            return petEntityMapper.fromEntityToDto(pet);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "pet not found"
            );
        }

    }

    @GetMapping
    public List<PetDTO> getPets(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        return petService.getOwnersPets(ownerId)
                .stream().
                map(pet -> petEntityMapper.fromEntityToDto(pet)).
                collect(Collectors.toList());
//        throw new UnsupportedOperationException();
    }
}
