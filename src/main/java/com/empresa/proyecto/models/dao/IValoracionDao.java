package com.empresa.proyecto.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.empresa.proyecto.models.entity.Valoracion;

public interface IValoracionDao extends CrudRepository<Valoracion, Long>{

	@Query("select v from Valoracion v join fetch v.usuario u where u.username=?1")
	public Valoracion getByUsername(String username); 
	
	@Query("select v from Valoracion v join fetch v.usuario u where u.id=?1")
	public Valoracion getByIdUsuario(Long id); 
	
	
	@Query("select v from Valoracion v join fetch v.usuario u join fetch v.producto p where u.username=?1 and p.id=?2")
	public Valoracion getByUsernameAndProducto(String username, Long id_producto); 
	
}
