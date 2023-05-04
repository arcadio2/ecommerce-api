package com.empresa.proyecto.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.empresa.proyecto.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto,Long>{
	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> getByNombre(String nombre);
	
	
	@Query("select p from Producto p join fetch p.detalle d where d.id=?1")
	public Producto getProductoByIdDetalle(Long  id);
	
	@Query("select p from Producto p join fetch p.categoria c where c.tipo=?1")
	public List<Producto> getByCategoria(String categoria);
	
	@Query("select p from Producto p join fetch p.detalle d join fetch d.talla t where t.talla in ?1")
	public List<Producto> getByTalla(List<String> talla);
	
	@Query("select p from Producto p join fetch p.detalle d join fetch d.color c where c.color in ?1")
	public List<Producto> getByColor(List<String> color);
	
	
	@Query("select distinct p from Producto p join fetch p.detalle d join fetch d.color c "
			+ "join fetch d.talla t where c.color in ?1 and t.talla in ?2")
	public List<Producto> getByColorAndTalla(List<String> talla,List<String> color);
	
	@Query("select distinct p from Producto p join fetch p.categoria a join fetch p.detalle d join fetch d.color c "
			+ "join fetch d.talla t where c.color in ?1 and t.talla in ?2 and a.tipo=?3")
	public List<Producto> getByColorAndTallaAndCategoria(List<String> talla,List<String> color,String categoria);
	
	@Query("select distinct p from Producto p join fetch p.categoria a join fetch p.detalle d join fetch d.color c "
			+ "join fetch d.talla t where c.color in ?2 or t.talla in ?1 or a.tipo=?3")
	public List<Producto> getByColorOrTallaOrCategoria(List<String> talla,List<String> color,String categoria);
	
	@Query("select p from Producto p  where p.isHombre=?1")
	public List<Producto> getBySexo(boolean isHombre);
	
	@Query("select p from Producto p join fetch p.categoria c where p.isHombre=?1  and c.tipo=?2")
	public List<Producto> getBySexoAndCategoria (boolean isHombre,String categoria);
	
	
	public Producto findByNombre(String nombre); 
	
	//@Query("select e from Ejercicio e join fetch e.musculo m where e.nombre like %?1% and m.id in ?2")
	
	
	//select producto.nombre,colores.color,tallas.talla from detalle_producto inner join producto on producto_id=producto.id inner join colores on color_id=colores.id inner join tallas on talla_id=tallas.id;
}
