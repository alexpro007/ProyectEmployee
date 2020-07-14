package com.employee.api.csv.upload;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.employee.api.entity.Employee;
import com.employee.api.dao.EmployeeDao;

@Service
public class CSVService {
	@Autowired
	EmployeeDao employeeDao;

	public void save(MultipartFile file) throws ParseException {
		System.out.println(file.toString());
		try {
			List<Employee> employee = CSVHelper.csvToEmployee(file.getInputStream());
			System.out.println("Guardar registros en ");
			employeeDao.saveAll(employee);
		} catch (IOException e) {
			System.out.println("Error al Cargar la Info ");
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}
}
