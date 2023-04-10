package com.empresa.proyecto.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.empresa.proyecto.models.entity.Bolsa;

public interface IBolsaDao extends CrudRepository<Bolsa, Long>{
	
	@Query("select b from Bolsa b join fetch b.usuario u where u.username=?1")
	public Bolsa getByUsername(String username); 

}
