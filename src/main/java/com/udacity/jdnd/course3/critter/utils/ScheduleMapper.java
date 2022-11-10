package com.udacity.jdnd.course3.critter.utils;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.entity.User;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ScheduleMapper implements DtoEntityMapper<Schedule, ScheduleDTO> {
    @Autowired
    PetRepository petRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public Schedule fromDtoToEntity(ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(dto,schedule);
        return schedule;
    }

    @Override
    public ScheduleDTO fromEntityToDto(Schedule entity) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(entity,scheduleDTO);
        if(entity.getEmployees() != null)
        {
            scheduleDTO.setEmployeeIds(
                    entity.getEmployees().stream()
                            .map(User::getId)
                            .collect(Collectors.toList())
            );
        }
        if(entity.getPets() != null)
        {
            scheduleDTO.setPetIds(
                    entity.getPets().stream()
                            .map(Pet::getId)
                            .collect(Collectors.toList())
            );
        }
        return scheduleDTO;
    }
}
