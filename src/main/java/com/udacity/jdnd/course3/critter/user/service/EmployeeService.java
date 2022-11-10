package com.udacity.jdnd.course3.critter.user.service;

//import java.util.Pair;
//import com.sun.tools.javac.util.Pair;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
//import java.util.

@Service
@Transactional
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
//        Set<Employee> selectedEmployees = employeeRepository.findAllByDaysAvailableContainingAndSkillsIsIn(dayOfWeek,employeeSkills);
        Set<Employee> employees = employeeRepository.findAllByDaysAvailableContaining(dayOfWeek);
        Set<Employee> selectedEmployees = new HashSet<>();
        for(Employee employee:employees)
        {
            Set<EmployeeSkill> requredSkills = new HashSet<>(employeeSkills);
            requredSkills.removeAll(employee.getSkills());
            if(requredSkills.isEmpty())
            {
                selectedEmployees.add(employee);
            }
        }
        //  gitting minimum number of employees to fit requirements
//        PriorityQueue<Pair<Employee,Integer>> employeeResults = new PriorityQueue<>(Collections.reverseOrder((emp1,emp2)->emp1.getSecond()-emp2.getSecond()));
//
//        for(Employee employee:employees)
//        {
//            Set<EmployeeSkill> requredSkills = new HashSet<>(employeeSkills);
//            requredSkills.retainAll(employee.getSkills());
//            Integer result = requredSkills.size();
//            employeeResults.add( Pair.of(employee,result));
//
//        }
//        Set<Employee> selectedEmployees = new HashSet<>();
//
//        while (! employeeResults.isEmpty() && ! employeeSkills.isEmpty())
//        {
//            Pair<Employee,Integer> employeePair  = employeeResults.remove();
//            selectedEmployees.add(employeePair.getFirst());
//            employeeSkills.removeAll(employeePair.getFirst().getSkills());
//        }
//

        return selectedEmployees;
    }
}
