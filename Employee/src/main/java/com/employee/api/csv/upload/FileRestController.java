package com.employee.api.csv.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import org.apache.catalina.connector.OutputBuffer;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.employee.api.dao.EmployeeDao;
import com.employee.api.entity.Employee;

@RestController
@RequestMapping("/csv")
public class FileRestController {
	
	private EmployeeDao employeeDao;
	
	private static String UPLOAD_DIR = "uploads";
	
	@PostMapping(value = "addMasivEmployee", consumes = { MimeTypeUtils.ALL_VALUE })
	public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		try {
			String fileName = file.getOriginalFilename();
			String path = request.getServletContext().getContextPath() 
					+ UPLOAD_DIR 
					+ File.separator 
					+ fileName;
			saveFile(file.getInputStream(), path);
			importCSV(path);
			return fileName;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private void saveFile(InputStream inputStream, String path) {
		try {
			OutputStream outPutStream = new FileOutputStream(new File(path));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outPutStream.write(bytes, 0, read);
			}
			outPutStream.flush();
			outPutStream.close();
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	private void importCSV(String path) {
		try {
			ExcelHelper excelHelper = new ExcelHelper(path);
			List<Employee> employees =excelHelper.readData(Employee.class.getName());
			employeeDao.saveAll(employees);
		} catch (Exception e) {
			e.getMessage();
		}
	}
}
