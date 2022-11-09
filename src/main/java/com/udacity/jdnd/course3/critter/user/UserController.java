package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.user.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import com.udacity.jdnd.course3.critter.utils.DtoEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private DtoEntityMapper<Customer, CustomerDTO> customerMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DtoEntityMapper<Employee, EmployeeDTO> employeeMapper;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {

        Customer customer = customerService.createCustomer(customerMapper.fromDtoToEntity(customerDTO));
        return customerMapper.fromEntityToDto(customer);
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {

        return customerService.getAllCustomers().stream().map(customer -> customerMapper.fromEntityToDto(customer)).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Customer customer = null;
        try {
            customer = customerService.findCustomerByPetId(petId);
            return  customerMapper.fromEntityToDto(customer);

        } catch (ChangeSetPersister.NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "pet not found"
            );
        }
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.createEmployee(employeeMapper.fromDtoToEntity(employeeDTO));
        return employeeMapper.fromEntityToDto(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        try {
            Employee employee = employeeService.getEmployee(employeeId);
            return employeeMapper.fromEntityToDto(employee);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "employee not found"
            );
        }

    }

    @PutMapping("/employee/{employeeId}")
    public EmployeeDTO setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {

        Employee employee = null;
        try {
            employee = employeeService.updateEmployeeSchedule(daysAvailable, employeeId);
            return employeeMapper.fromEntityToDto(employee);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "employee not found"
            );
        }

    }

    @GetMapping("/employee/availability")
    public Set<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return employeeService.getAvailableEmployees(employeeDTO)
                .stream()
                .map(employee -> employeeMapper.fromEntityToDto(employee))
                .collect(Collectors.toSet());
    }

}
