package com.empresa.proyecto.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.proyecto.models.dao.ITallaDao;
import com.empresa.proyecto.models.entity.Talla;


@Service
public class TallaService implements ItallaService{

	@Autowired
	private ITallaDao tallaDao;

	@Override
	public List<Talla> findAll() {
		// TODO Auto-generated method stub
		return (List<Talla>) tallaDao.findAll();
	}

	@Override
	public Talla findById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Talla> findByTronco(boolean isSuperior) {
		// TODO Auto-generated method stub
		return tallaDao.getTallaBytronco_superior(isSuperior);
	} 
	
	
}
