package com.empresa.proyecto.models.service;

import com.empresa.proyecto.models.entity.Valoracion;

public interface IValoracionService {
	
	public Valoracion getOneByIdUsuario(Long id); 
	public Valoracion getOneByUsername(String username); 
	
	public Valoracion save(Valoracion valoracion); 
	
	public Valoracion getByUsernameAndProducto(String username,Long id_producto); 
	
	
	
}
