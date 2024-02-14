package com.crud.global.exception.handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> GlobalException(Exception ex)
	{
		System.out.println(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
	}
	
}
