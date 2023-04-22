package com.empresa.proyecto.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.proyecto.models.dao.ICategoriaProductoDao;
import com.empresa.proyecto.models.entity.CategoriaProducto;

@Service
public class CategoriaProductoService implements ICategoriaProductoService{

	
	@Autowired
	private ICategoriaProductoDao categoriaDao; 
	
	@Override
	public List<CategoriaProducto> findAll() {
		// TODO Auto-generated method stub
		return (List<CategoriaProducto>) categoriaDao.findAll();
	}

	@Override
	public List<CategoriaProducto> finBySexo(String sexo) {
		if(sexo.equalsIgnoreCase("hombre")) {
			return categoriaDao.getByHombre();
		}else {
			return categoriaDao.getByMujer();
		}
		
	}

	@Override
	public CategoriaProducto save(CategoriaProducto categoria) {
		// TODO Auto-generated method stub
		return categoriaDao.save(categoria);
	}

	@Override
	public void delete(CategoriaProducto categoria) {
		categoriaDao.delete(null);
		
	}

}
