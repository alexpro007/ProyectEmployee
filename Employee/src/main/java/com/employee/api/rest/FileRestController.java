package com.employee.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.employee.api.csv.upload.CSVHelper;
import com.employee.api.csv.upload.CSVService;
import com.employee.api.exception.ResponseMessage;

@RestController
@RequestMapping("/csv")
public class FileRestController {
	
	@Autowired
	CSVService fileService;

	//private static String UPLOAD_DIR = "uploads";

	@PostMapping("/uploadEmployees")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";
		if (CSVHelper.hasCSVFormat(file)) {
			try {
				fileService.save(file);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
		        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Please upload a csv file!";
		return ResponseEntity.ok().body(null);
	}
}
