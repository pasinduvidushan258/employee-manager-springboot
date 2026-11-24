package com.example.employee_manager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employee_manager.model.Employee;
import com.example.employee_manager.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id: " + id));
    }

    public void saveEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("This email is already registered");
        }
        employeeRepository.save(employee);
    }

    public void updateEmployee(Long id, Employee updatedEmployee) {
        Employee existing = getEmployeeById(id);

        if (employeeRepository.existsByEmailAndIdNot(updatedEmployee.getEmail(), id)) {
            throw new IllegalArgumentException("This email is already registered");
        }

        existing.setFullName(updatedEmployee.getFullName());
        existing.setEmail(updatedEmployee.getEmail());
        existing.setPhone(updatedEmployee.getPhone());
        existing.setDepartment(updatedEmployee.getDepartment());
        existing.setSalary(updatedEmployee.getSalary());

        employeeRepository.save(existing);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}