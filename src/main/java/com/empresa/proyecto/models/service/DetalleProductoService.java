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



	@Override
	public DetalleProducto getById(Long id) {
		// TODO Auto-generated method stub
		return detalleDao.findById(id).orElse(null);
	}



	@Override
	public DetalleProducto getByNombreProductoAndTallaAndColor(String nombre_producto, String talla, String color) {

		List<DetalleProducto> detalles = detalleDao.getByNombreProductoAndTallaAndColor(nombre_producto, talla, color);
		if(detalles.size()>0) {
			return detalles.get(0);
		}else {
			return null;
		}
		//return detalleDao.getByNombreProductoAndTallaAndColor(nombre_producto, talla, color);
	}



	@Override
	public DetalleProducto getByIdProductoAndTallaAndColor(Long id, String talla, String color) {
		// TODO Auto-generated method stub
		List<DetalleProducto> detalles = detalleDao.getByIdProductoAndTallaAndColor(id, talla, color);
		if(detalles.size()>0) {
			return detalles.get(0);
		}else {
			return null;
		}
	}

}
