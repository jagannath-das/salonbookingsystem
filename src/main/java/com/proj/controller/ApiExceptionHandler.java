package com.proj.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleIllegalArgument(
			IllegalArgumentException ex,
			HttpServletRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("error", "Bad Request");
		body.put("message", ex.getMessage());
		body.put("path", request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}
}
