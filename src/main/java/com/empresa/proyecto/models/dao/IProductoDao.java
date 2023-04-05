package com.empresa.proyecto.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.empresa.proyecto.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto,Long>{

	
	
}
