package com.empresa.proyecto.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;

public interface IValidationService {

	Map<String, Object> responseErrors(BindingResult result); 
	
}
