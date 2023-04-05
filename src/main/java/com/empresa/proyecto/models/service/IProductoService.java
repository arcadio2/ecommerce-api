package com.empresa.proyecto.models.service;

import java.util.List;

import com.empresa.proyecto.models.entity.Producto;

public interface IProductoService {
	
	public Producto getByNombre(); 
	
	public List<Producto> findByNombre(); 

}
