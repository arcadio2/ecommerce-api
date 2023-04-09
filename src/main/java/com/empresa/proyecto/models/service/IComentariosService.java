package com.empresa.proyecto.models.service;

import com.empresa.proyecto.models.entity.Comentarios;

public interface IComentariosService {

	public Comentarios save(Comentarios comentario); 
	
	public Comentarios getByUsernameAndPerfil(String username,Long id); 
	
}
