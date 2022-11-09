package com.udacity.jdnd.course3.critter.utils;

import com.udacity.jdnd.course3.critter.user.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEntityMapper implements DtoEntityMapper<Employee, EmployeeDTO> {
    @Override
    public Employee fromDtoToEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto,employee);
        return employee;
    }

    @Override
    public EmployeeDTO fromEntityToDto(Employee entity) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(entity,employeeDTO);
        return employeeDTO;
    }
}
