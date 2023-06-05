package com.empresa.proyecto.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.empresa.proyecto.models.entity.DetalleProducto;

public interface IDetalleProductoDao extends CrudRepository<DetalleProducto, Long>{
	
	
	@Query("select d from DetalleProducto d "
			+ "join fetch d.color c join fetch d.talla t"
			+ " join fetch d.producto p"
			+ " where p.nombre=?1 and t.talla=?2 and c.color=?3 and d.stock > 0")
	public List<DetalleProducto> getByNombreProductoAndTallaAndColor(String nombre_producto,String talla, String color); 

	
	@Query("select d from DetalleProducto d "
			+ "join fetch d.color c join fetch d.talla t"
			+ " join fetch d.producto p"
			+ " where p.id=?1 and t.talla=?2 and c.color=?3 and d.stock > 0")
	public List<DetalleProducto> getByIdProductoAndTallaAndColor(Long id,String talla, String color);
	/*
	@Query(value="select * from detalle_producto inner join "
			+ "producto on producto_id=producto.id inner join colores on color_id=colores.id "
			+ "inner join tallas on talla_id = tallas.id "
			+ " where tallas.talla=?2 and producto.nombre=?1 and colores.color=?3 ",nativeQuery=true)
	public List<DetalleProducto> getByNombreProductoAndTallaAndColor(String nombre_producto,String talla, String color); 
	*/
}
