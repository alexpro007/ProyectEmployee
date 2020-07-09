package com.employee.api.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.employee.api.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Integer> {
	
	//Select Employee where state is ACTIVE
	@Query(value="SELECT * FROM EMPLOYEE E WHERE STATUS=?1 ORDER BY ID ASC" ,nativeQuery=true)
	List<Employee> findByStatus(String status);
	
	//Select Employee where state is id selected and state is ACTIVE
	@Query(value="SELECT * FROM EMPLOYEE E WHERE ID=?1 AND STATUS=?2 ORDER BY ID ASC" ,nativeQuery=true)
	Optional<Employee> findByIdAndStatus(Integer id, String status);
	
	@Query(value="UPDATE EMPLOYEE SET STATUS=?2 WHERE ID=?1" ,nativeQuery=true)
	Employee deleteByIdAndStatus(Integer id, String status);


}
