package com.empresa.proyecto.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.empresa.proyecto.models.entity.Comentarios;


public interface IComentariosDao extends CrudRepository<Comentarios, Long>{

	@Query("select c from Comentarios c join fetch c.usuario u join fetch c.producto p where u.username=?1 and p.id=?2")
	public Comentarios getByUsernameAndProducto(String username, Long id_producto); 
	
	
	@Query("select c from Comentarios c  join fetch c.producto p where p.id=?1")
	public List<Comentarios> getByIdProducto(Long id_producto); 
	
}
