package com.empresa.proyecto.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.empresa.proyecto.models.entity.Usuario;



public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
	public Usuario findByUsername(String username);
	@Query("select u from Usuario u where u.username=?1")
	public Usuario finByUsername2(String username);
	
	@Query("select u from Usuario u where u.token=?1")
	public Usuario getByToken(String token);
	
	@Query("select u from Usuario u where u.email=?1")
	public Usuario finByEmail(String email);
	
	@Query("select u from Usuario u join fetch u.roles r where r.nombre=?1")
	public List<Usuario> findUserByRole(String role);

}
