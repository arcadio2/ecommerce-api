package com.empresa.proyecto.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.proyecto.models.dao.IColoresDao;
import com.empresa.proyecto.models.entity.Color;


@Service
public class ColoresService implements IColoresService{
	
	@Autowired 
	private IColoresDao coloresDao;

	@Override
	public List<Color> findAll() {
		// TODO Auto-generated method stub
		return (List<Color>) coloresDao.findAll();
	}

	@Override
	public Color findById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color findByColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color findByHexadecimal() {
		// TODO Auto-generated method stub
		return null;
	} 
	

}
