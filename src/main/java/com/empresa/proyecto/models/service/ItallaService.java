package com.empresa.proyecto.models.service;

import java.util.List;

import com.empresa.proyecto.models.entity.Talla;

public interface ItallaService {

	public List<Talla> findAll();
	
	public Talla findById(Long  id); 
	
	public List<Talla> findByTronco(boolean isSuperior); 
	
	
}
