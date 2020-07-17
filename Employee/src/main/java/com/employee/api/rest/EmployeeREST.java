package com.employee.api.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.employee.api.exception.ResourceNotFoundException;

import com.employee.api.dao.EmployeeDao;
import com.employee.api.entity.Employee;

@CacheEvict(value="fooCache",allEntries=true)
@RestController
@RequestMapping("/")
public class EmployeeREST {

	@Autowired
	private EmployeeDao employeeDao;

	// Get employees by an ID
	@GetMapping
	@RequestMapping("/getEmployee/{id}")
	public ResponseEntity<Employee> getEmployeeId(@PathVariable(value = "id") Integer id) {
		Optional<Employee> employee = employeeDao.findByIdAndStatus(id, "ACTIVE");
		return ResponseEntity.ok()
				.body(employee.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id)));
	}

	// Create new employees where the status is ACTIVE
	@PostMapping("/addEmployee")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		employee.setStatus("ACTIVE");
		Employee addEmployee = employeeDao.save(employee);
		return ResponseEntity.ok(addEmployee);
	}

	// Update existing employees
	@PutMapping("/updateEmployee")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
		Optional<Employee> optionalEmployee = employeeDao.findById(employee.getId());
		if (optionalEmployee.isPresent()) {
			if (employee.getStatus().equals("ACTIVE") || employee.getStatus().equals("INACTIVE")) {
				Employee updateEmployee = optionalEmployee.get();
				updateEmployee.setFirstName(employee.getFirstName());
				updateEmployee.setMiddleInitial(employee.getMiddleInitial());
				updateEmployee.setLastName(employee.getLastName());
				System.out.println(employee.getDateOfBirth());
				updateEmployee.setDateOfBirth(employee.getDateOfBirth());
				System.out.println(employee.getDateOfEmployment());
				updateEmployee.setDateOfEmployment(employee.getDateOfEmployment());
				updateEmployee.setStatus(employee.getStatus());
				employeeDao.save(updateEmployee);
				return ResponseEntity.ok(updateEmployee);
			}
		} else {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	// Delete employees
	@DeleteMapping("/auth/deleteEmployee/{id}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Integer id) {
		try {
			if (employeeDao.existsById(id)) {
				employeeDao.deleteByIdAndStatus(id, "INACTIVE");
				return ResponseEntity.ok(null);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	// Get all employees @GetMapping
	@RequestMapping("/getAllEmployees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees = (List<Employee>) employeeDao.findByStatus("ACTIVE");
		return ResponseEntity.ok(employees);
	}

}
