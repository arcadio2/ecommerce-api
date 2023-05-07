package com.empresa.proyecto.models.service;

import java.util.List;

import com.empresa.proyecto.models.entity.Comentarios;

public interface IComentariosService {

	public Comentarios save(Comentarios comentario); 
	
	public Comentarios getByUsernameAndPerfil(String username,Long id); 
	
	public List<Comentarios> getByIdProducto(Long id_producto); 
	
}
