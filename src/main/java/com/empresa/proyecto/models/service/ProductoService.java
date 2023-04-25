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

	@Override
	public List<Producto> getByCategoria(String categoria) {
		// TODO Auto-generated method stub
		return productoDao.getByCategoria(categoria);
	}

	@Override
	public List<Producto> getByTalla(List<String> talla) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Producto> getByColor(List<String> color) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Producto> getByColorAndTalla(List<String> talla, List<String> color) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Producto> getByColorAndTallaAndCategoria(List<String> talla, List<String> color, String categoria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Producto> getByColorOrTallaOrCategoria(List<String> talla, List<String> color, String categoria) {
		// TODO Auto-generated method stub
		return productoDao.getByColorOrTallaOrCategoria(talla, color, categoria);
	}

	@Override
	public List<Producto> getBySexo(boolean isHombre) {
		// TODO Auto-generated method stub
		return productoDao.getBySexo(isHombre);
	}

	@Override
	public List<Producto> getBySexoAndCategoria(boolean isHombre, String categoria) {
		// TODO Auto-generated method stub
		return productoDao.getBySexoAndCategoria(isHombre, categoria);
	}



	
}
