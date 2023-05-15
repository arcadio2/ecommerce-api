package com.empresa.proyecto.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.empresa.proyecto.models.entity.Bolsa;

public interface IBolsaDao extends JpaRepository<Bolsa, Long>{
	
	@Query("select b from Bolsa b join fetch b.usuario u where u.username=?1")
	public List<Bolsa> getByUsername(String username); 
	
	@Modifying
	@Query("DELETE FROM Bolsa b WHERE b.usuario.username = :username")
	void deleteByUsuarioUsername(@Param("username") String username);


}
