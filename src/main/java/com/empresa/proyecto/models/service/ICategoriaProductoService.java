package com.empresa.proyecto.models.service;

import java.util.List;

import com.empresa.proyecto.models.entity.CategoriaProducto;

public interface ICategoriaProductoService {

	public List<CategoriaProducto> findAll(); 
	
	public List<CategoriaProducto> finBySexo(String sexo);
	
	public CategoriaProducto save(CategoriaProducto categoria); 
	
	public void delete(CategoriaProducto categoria); 
	
	
	
	
}
