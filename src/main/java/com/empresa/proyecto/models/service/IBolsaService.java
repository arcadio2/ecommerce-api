package com.empresa.proyecto.models.service;

import java.util.List;

import com.empresa.proyecto.models.entity.Bolsa;

public interface IBolsaService {
	
	public Bolsa getById(Long id); 
	
	public List<Bolsa> getByUsername(String username); 
	
	public Bolsa getByUsernameAndDetalle(String username,Long id_detalle); 
	
	public Bolsa save(Bolsa bolsa); 
	
	public void delete(Bolsa bolsa); 
	
	
	

}
