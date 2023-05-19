package com.empresa.proyecto.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.proyecto.models.dao.IComprasDao;
import com.empresa.proyecto.models.entity.Compra;


@Service
public class CompraService implements ICompraService{

	
	@Autowired
	private IComprasDao comprasDao; 
	
	@Override
	@Transactional
	public Compra save(Compra compra) {
		// TODO Auto-generated method stub
		return comprasDao.save(compra);
	}

	@Override
	public List<Compra> getByUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Compra> getAll() {
		// TODO Auto-generated method stub
		return comprasDao.findAll();
	}

	@Override
	public void delete(Compra compra) {
		comprasDao.delete(compra);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Compra> saveAll(List<Compra> compras) {
		// TODO Auto-generated method stub
		return comprasDao.saveAll(compras);
	}

}
