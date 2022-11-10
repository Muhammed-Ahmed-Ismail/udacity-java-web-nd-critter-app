package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.utils.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    ScheduleMapper scheduleMapper;
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.createSchedule(scheduleDTO);
        return  scheduleMapper.fromEntityToDto(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        return scheduleService.getAllSchedules()
                .stream()
                .map(schedule -> scheduleMapper.fromEntityToDto(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        return scheduleService.getAllSchedulesByPetId(petId).stream()
                .map(schedule -> scheduleMapper.fromEntityToDto(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getAllSchedulesByEmployeeId(employeeId).stream()
                .map(schedule -> scheduleMapper.fromEntityToDto(schedule))
                .collect(Collectors.toList());

    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.getAllSchedulesByOwnerId(customerId).stream()
                .map(schedule -> scheduleMapper.fromEntityToDto(schedule))
                .collect(Collectors.toList());
    }
}
