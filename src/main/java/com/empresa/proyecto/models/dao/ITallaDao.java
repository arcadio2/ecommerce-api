package com.empresa.proyecto.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.empresa.proyecto.models.entity.Talla;

public interface ITallaDao extends CrudRepository<Talla, Long>{

	
	@Query("select t from Talla t where t.tronco_superior=?1")
	public List<Talla> getTallaBytronco_superior(boolean talla); 
}
