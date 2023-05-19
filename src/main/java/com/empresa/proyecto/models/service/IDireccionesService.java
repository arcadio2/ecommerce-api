package com.empresa.proyecto.models.service;

import com.empresa.proyecto.models.entity.Direccion;

public interface IDireccionesService {

	
	public Direccion save(Direccion direccion); 
	
	public Direccion getById(Long id); 
	
	public void delete(Direccion direccion); 
	
	
}
