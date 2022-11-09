package com.udacity.jdnd.course3.critter.utils;

public interface DtoEntityMapper <E,D> {
    E fromDtoToEntity(D dto);
    D fromEntityToDto(E entity);
}