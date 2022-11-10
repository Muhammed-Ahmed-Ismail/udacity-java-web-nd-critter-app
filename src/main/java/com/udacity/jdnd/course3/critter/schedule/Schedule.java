package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.entity.Employee;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {
    @Id
    @GeneratedValue
    private Long id;
    @ElementCollection
    private List<EmployeeSkill> activities;

    private LocalDate date;

    @ManyToMany
    private List<Employee> employees;

    @ManyToMany
    private List<Pet>  pets;



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(List<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

}
