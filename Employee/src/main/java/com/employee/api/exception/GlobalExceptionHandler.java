package com.employee.api.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	// handler specific exception
	// handle global
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<DetailException> handleResourseNotFoundExeption(ResourceNotFoundException exception, WebRequest request) {
		DetailException detailException = new DetailException(new Date(), exception.getMessage(),
				request.getDescription(false));
		return new ResponseEntity(detailException, HttpStatus.NOT_FOUND);
	}
}