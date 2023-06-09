package com.empresa.proyecto.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.proyecto.models.dao.IBolsaDao;
import com.empresa.proyecto.models.entity.Bolsa;

@Service
public class BolsaService implements IBolsaService{

	@Autowired
	private IBolsaDao bolsaDao; 
	
	@Override
	public Bolsa getById(Long id) {
		return bolsaDao.findById(id).orElse(null);
	}

	@Override
	public List<Bolsa> getByUsername(String username) {
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

	@Override
	public void delete(Bolsa bolsa) {
		bolsaDao.delete(bolsa);
		
	}

	@Override
	@Transactional

	public void deleteAllByUSername(String username) {
		bolsaDao.deleteByUsuarioUsername(username);
		
	}
	
	@Override
	public void vaciarBolsaUsuario(String username) {
	    List<Bolsa> bolsaUsuario = bolsaDao.getByUsername(username);
	    bolsaDao.deleteAll(bolsaUsuario);
	}


}
