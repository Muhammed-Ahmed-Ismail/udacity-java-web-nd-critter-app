package com.udacity.jdnd.course3.critter.user.repository;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
     Set<Employee> findAllByDaysAvailableContaining(DayOfWeek dayOfWeek);
//     @Query("select e from Employee e where e.daysAvailable like concat('%', ?1, '%') and e.skills = ?2")
     Set<Employee> findAllByDaysAvailableContainingAndSkillsIsIn(DayOfWeek dayOfWeek , Set<EmployeeSkill> employeeSkills);
}
