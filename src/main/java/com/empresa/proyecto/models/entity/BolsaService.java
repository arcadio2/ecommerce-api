package com.empresa.proyecto.models.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.proyecto.models.dao.IBolsaDao;
import com.empresa.proyecto.models.service.IBolsaService;

@Service
public class BolsaService implements IBolsaService{

	@Autowired
	private IBolsaDao bolsaDao; 
	
	@Override
	public Bolsa getById(Long id) {
		return bolsaDao.findById(id).orElse(null);
	}

	@Override
	public Bolsa getByUsername(String username) {
		return bolsaDao.getByUsername(username);
	}

	@Override
	public Bolsa getByUsernameAndDetalle(String username, Long id_detalle) {
		return null;
	}

	@Override
	public Bolsa save(Bolsa bolsa) {
		return bolsaDao.save(bolsa);
	}

}
