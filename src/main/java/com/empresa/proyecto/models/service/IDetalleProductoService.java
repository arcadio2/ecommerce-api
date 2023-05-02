package com.empresa.proyecto.models.service;

import java.util.List;

import com.empresa.proyecto.models.entity.DetalleProducto;

public interface IDetalleProductoService {
	
	public List<DetalleProducto> getByIdProducto(Long producto_id);
	
	
	
	public DetalleProducto save(DetalleProducto detalle);
	
	
	public DetalleProducto getById(Long id); 
	
	public DetalleProducto getByNombreProductoAndTallaAndColor(String nombre_producto,String talla, String color); 
	
	public DetalleProducto getByIdProductoAndTallaAndColor(Long id,String talla, String color);

}
