package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.utils.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    ScheduleMapper scheduleMapper;
    public Schedule createSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = scheduleMapper.fromDtoToEntity(scheduleDTO);
        Schedule savedSchedule = scheduleRepository.save(schedule);
//        List<Employee> employeesOfSchedule = schedule.getEmployees();
//        List<Pet> petsOfSchedule = schedule.getPets();
        List<Long> employeesOfScheduleIds = scheduleDTO.getEmployeeIds();
        List <Long> petsOfScheduleIds = scheduleDTO.getPetIds();
         if(employeesOfScheduleIds != null)
        {
            List<Employee> employees = employeeRepository.findAllByIdIn(employeesOfScheduleIds);
            savedSchedule.setEmployees(employees);
            for(Employee employee : employees)
            {
//                Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
//                Employee employee = optionalEmployee.orElseThrow(ChangeSetPersister.NotFoundException::new);
                List<Schedule> employeeSchedules = employee.getSchedules();
                if(employeeSchedules == null)
                {
                    employeeSchedules = new ArrayList<>();
                }
                employeeSchedules.add(savedSchedule);
                employeeRepository.save(employee);
            }
        }
         if(petsOfScheduleIds != null)
         {
             List<Pet> pets = petRepository.findAllByIdIn(petsOfScheduleIds);
             savedSchedule.setPets(pets);
             for (Pet pet : pets)
             {
//                 Optional<Pet> optionalPet = petRepository.findById(petId);
//                 Pet pet = optionalPet.orElseThrow(ChangeSetPersister.NotFoundException::new);
                 List<Schedule> petsSchedule = pet.getSchedules();
                 if(petsSchedule == null)
                 {
                     petsSchedule = new ArrayList<>();
                 }
                 petsSchedule.add(savedSchedule);
                 petRepository.save(pet);
             }
         }
         scheduleRepository.save(savedSchedule);
        return savedSchedule;
    }
    public List<Schedule> getAllSchedules()
    {
        return scheduleRepository.findAll();
    }
    public List<Schedule> getAllSchedulesByPetId(Long id)
    {
        return scheduleRepository.findAllByPetsId(id);
    }

    public List<Schedule> getAllSchedulesByEmployeeId(Long id)
    {
        return scheduleRepository.findAllByEmployeesId(id);
    }
    public List<Schedule> getAllSchedulesByOwnerId(Long id)
    {
        return scheduleRepository.findAllByPetsOwnerId(id);
    }
}
