package com.empresa.proyecto.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.empresa.proyecto.models.entity.Role;
import com.empresa.proyecto.models.entity.Usuario;

public interface RolDao extends CrudRepository<Role, Long>{

	@Query("select r from Role r where r.nombre=?1")
	Role getRoleByName(String name);
}
