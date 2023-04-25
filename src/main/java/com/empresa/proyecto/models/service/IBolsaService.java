package com.empresa.proyecto.models.service;

import com.empresa.proyecto.models.entity.Bolsa;

public interface IBolsaService {
	
	public Bolsa getById(Long id); 
	
	public Bolsa getByUsername(String username); 
	
	public Bolsa getByUsernameAndDetalle(String username,Long id_detalle); 
	
	public Bolsa save(Bolsa bolsa); 
	
	public void delete(Bolsa bolsa); 
	
	
	

}
