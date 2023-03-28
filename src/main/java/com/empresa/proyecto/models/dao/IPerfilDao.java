package com.empresa.proyecto.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.empresa.proyecto.models.entity.Perfil;



public interface IPerfilDao extends CrudRepository<Perfil, Long>{
	@Query("select p from Perfil p join fetch p.usuario u where u.username=?1")
	//@Query("select f from Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id=?1")
	public Perfil getPerfilByUsername(String username); 
	
	@Query("select p from Perfil p  where p.instructor=?1")
	public List<Perfil> getPerfilesByInstructor(String instructor); 
	
	@Query("select p from Perfil p join fetch p.usuario u join fetch u.roles r where r.nombre=?1")
	public List<Perfil> getPerfilesByRole(String nombre);

	
}
