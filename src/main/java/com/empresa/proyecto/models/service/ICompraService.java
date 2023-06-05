package com.empresa.proyecto.models.service;

import java.util.List;

import com.empresa.proyecto.models.entity.Compra;

public interface ICompraService {
	
	public Compra save(Compra compra);
	
	public List<Compra> getByUser(String username); 
	
	public List<Compra> getAll(); 
	
	public Compra getById(Long id);
	 
	public void delete(Compra compra); 
	
	public List<Compra> saveAll(List<Compra> compras);
	
	public Compra getByProductoAndUsuario(String nombre,Long id_producto);
	
	public boolean getByProductoAndUsuarioExist(String nombre,Long id_producto);

}
