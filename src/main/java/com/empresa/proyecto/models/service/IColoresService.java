package com.empresa.proyecto.models.service;

import java.util.List;

import com.empresa.proyecto.models.entity.Color;

public interface IColoresService {

	List<Color> findAll(); 
	
	Color findById(); 
	
	Color findByColor(); 
	
	Color findByHexadecimal();
	
}
