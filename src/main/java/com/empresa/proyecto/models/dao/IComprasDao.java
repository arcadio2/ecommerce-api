package com.empresa.proyecto.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.empresa.proyecto.models.entity.Compra;

public interface IComprasDao extends JpaRepository<Compra, Long>{
	
	@Query("SELECT c from Compra c join fetch c.usuario u where u.username=?1")
	public List<Compra> getByUsername(String username);

}
