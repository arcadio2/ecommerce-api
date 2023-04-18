package com.empresa.proyecto.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.empresa.proyecto.models.entity.CategoriaProducto;

public interface ICategoriaProductoDao extends CrudRepository<CategoriaProducto, Long>{

	@Query("select c from CategoriaProducto c join fetch c.sexo s where s.sexo=?1")
	public List<CategoriaProducto> getBySexo(String sexo); 
	
	 
}
