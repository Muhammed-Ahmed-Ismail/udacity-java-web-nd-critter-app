package com.udacity.jdnd.course3.critter.utils;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PetEntityMapper implements DtoEntityMapper<Pet, PetDTO>{
    @Override
    public Pet fromDtoToEntity(PetDTO dto) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(dto,pet);
        return pet;
    }

    @Override
    public PetDTO fromEntityToDto(Pet entity) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(entity,petDTO);
        petDTO.setOwnerId(entity.getOwner().getId());
        return petDTO;
    }
}
