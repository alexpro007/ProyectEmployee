package com.employee.api.csv.upload;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;
import com.employee.api.entity.Employee;

public class CSVHelper {
	public static String TYPE = "text/csv";
	//static String[] HEADERs = { "firstName", "middleInitial", "lastName", "dateOfBirth","dateOfEmployment" };

	public static boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			//System.out.println("Se valida archivo hasCSVFormat Valor False");
			return false;
		}
		//System.out.println("Se valida archivo hasCSVFormat Valor True");
		return true;
	}

	public static List<Employee> csvToEmployee(InputStream is) throws ParseException {
		System.out.println("csvToEmployee");
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			System.out.println("csvToEmployee Entra a Try");
			List<Employee> listEmployee = new ArrayList<Employee>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				System.out.println("Carga de datos");
				Employee employee =new Employee(
						csvRecord.get("firstName"),
						csvRecord.get("middleInitial"),
						csvRecord.get("lastName"),
						stringToDate(csvRecord.get("dateOfBirth")),
						stringToDate(csvRecord.get("dateOfEmployment")),
						csvRecord.get("status"));
				listEmployee.add(employee);
			}
			return listEmployee;
		} catch (IOException e) {
			//System.out.println("problema al cargar la info");
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}
	
	public static Date stringToDate(String dateOld) throws ParseException{
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateNew = formatter.parse(dateOld);
		java.sql.Date sqlStartDate = new java.sql.Date(dateNew.getTime()); 
        return sqlStartDate;
    }

}