package com.empresa.proyecto.services;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class ValidationService implements IValidationService{

	@Override
	public Map<String, Object> responseErrors(BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		if(!result.hasErrors()) {
			return null;
		}
		Map<String,String> errors = result.getFieldErrors()
				.stream()
				.collect(Collectors.toMap(
						e ->{
							return e.getField();
						}
						, 
						e->{
							return e.getDefaultMessage();
						}
						));
				  
		
		response.put("mensaje","Ocurrió un error al guardar, datos invalidos");
		response.put("errors", errors);
		return response;
		
	}

	
	
}
