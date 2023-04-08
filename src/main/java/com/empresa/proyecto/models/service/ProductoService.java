package com.empresa.proyecto.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.proyecto.models.dao.IProductoDao;
import com.empresa.proyecto.models.entity.Producto;

@Service
public class ProductoService implements IProductoService{
	
	
	@Autowired
	private IProductoDao productoDao; 
	
	@Override
	public List<Producto> findAll(){
		return (List<Producto>) productoDao.findAll(); 
	}

	@Override
	public Producto getByNombre(String nombre) {
		
		return productoDao.findByNombre(nombre);
	}

	@Override
	public List<Producto> findByNombre(String nombre) {
		
		return productoDao.getByNombre(nombre);
	}

	@Override
	public Producto findById(Long id) {
		return productoDao.findById(id).orElse(null); 
	}

	@Override
	public Producto save(Producto producto) {
		return productoDao.save(producto); 
	}

	@Override
	public void delete(Producto producto) {
		productoDao.delete(producto);
		
	}



	
}
