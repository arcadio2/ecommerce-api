package com.empresa.proyecto.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.proyecto.models.dao.IDetalleProductoDao;
import com.empresa.proyecto.models.entity.DetalleProducto;

@Service
public class DetalleProductoService implements IDetalleProductoService{
	
	@Autowired
	private IDetalleProductoDao detalleDao; 

	@Override
	public List<DetalleProducto> getByIdProducto(Long producto_id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public DetalleProducto save(DetalleProducto detalle) {
		// TODO Auto-generated method stub
		return detalleDao.save(detalle);
	}

}
