package com.empresa.proyecto.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.proyecto.models.dao.IDireccionesDao;
import com.empresa.proyecto.models.entity.Direccion;

@Service
public class DireccionesService implements IDireccionesService{

	@Autowired
	private IDireccionesDao direccionesDao; 
	
	@Override
	public Direccion save(Direccion direccion) {
		// TODO Auto-generated method stub
		return direccionesDao.save(direccion);
	}

	@Override
	public Direccion getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Direccion direccion) {
		// TODO Auto-generated method stub
		
	}

	
	
}
