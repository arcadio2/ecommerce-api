package com.empresa.proyecto.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.proyecto.models.dao.IValoracionDao;
import com.empresa.proyecto.models.entity.Valoracion;

@Service
public class ValoracionServiceImpl implements IValoracionService{

	@Autowired
	private IValoracionDao valoracionDao;

	@Override
	public Valoracion getOneByIdUsuario(Long id) {
		
		return valoracionDao.getByIdUsuario(id);
	}

	@Override
	public Valoracion getOneByUsername(String username) {

		return valoracionDao.getByUsername(username);
	}

	@Override
	public Valoracion save(Valoracion valoracion) {
		return valoracionDao.save(valoracion);
	}

	@Override
	public Valoracion getByUsernameAndProducto(String username, Long id_producto) {
		
		return valoracionDao.getByUsernameAndProducto(username, id_producto);
	} 
	
	
}
