package com.empresa.proyecto.models.service;

import java.util.List;

import com.empresa.proyecto.models.entity.Producto;

public interface IProductoService {
	
	public List<Producto> findAll(); 
	
	public Producto getByNombre(String nombre); 
	
	public List<Producto> findByNombre(String nombre); 
	
	public Producto findById(Long id); 
	
	public Producto save(Producto producto); 
	
	public void delete(Producto producto); 
	
	public List<Producto> getBySexo(boolean isHombre);
	public List<Producto> getBySexoAndCategoria(boolean isHombre,String categoria);
	public Producto getProductoByIdDetalle(Long  id);
	
	public List<Producto> getByCategoria(String categoria);
	
	public List<Producto> getByTalla(List<String> talla);
	
	public List<Producto> getByColor(List<String> color);
	
	public List<Producto> getByColorAndTalla(List<String> talla,List<String> color);
	
	public List<Producto> getByColorAndTallaAndCategoria(List<String> talla,List<String> color,String categoria);
	
	public List<Producto> getByColorOrTallaOrCategoria(List<String> talla,List<String> color,String categoria);

	
}
