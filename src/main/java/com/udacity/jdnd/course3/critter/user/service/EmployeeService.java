package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    public Employee createEmployee(Employee employee)
    {
        return employeeRepository.save(employee);
    }
    public Employee updateEmployeeSchedule(Set<DayOfWeek> days , Long employeeId) throws ChangeSetPersister.NotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        Employee employee = optionalEmployee.orElseThrow(ChangeSetPersister.NotFoundException::new);
        employee.setDaysAvailable(days);
        return employee;
    }
    public Employee getEmployee(Long employeeId) throws ChangeSetPersister.NotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        return optionalEmployee.orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public Set<Employee> getAvailableEmployees(EmployeeRequestDTO employeeRequestDTO)
    {
        LocalDate date = employeeRequestDTO.getDate();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        Set<EmployeeSkill> employeeSkills = employeeRequestDTO.getSkills();
        return employeeRepository.findAllByDaysAvailableContainingAndSkillsIn(dayOfWeek,employeeSkills);
    }
}
