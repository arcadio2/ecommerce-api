package com.empresa.proyecto.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.empresa.proyecto.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto,Long>{
	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> getByNombre(String nombre);
	
	
	public Producto findByNombre(String nombre); 
	
	//@Query("select e from Ejercicio e join fetch e.musculo m where e.nombre like %?1% and m.id in ?2")
}
