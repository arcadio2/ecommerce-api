package com.empresa.proyecto.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.proyecto.models.dao.IComentariosDao;
import com.empresa.proyecto.models.entity.Comentarios;

@Service
public class ComentariosServiceImpl implements IComentariosService{

	@Autowired
	private IComentariosDao comentarioDao; 
	
	@Override
	public Comentarios save(Comentarios comentario) {
		return comentarioDao.save(comentario);
	}
	
	
	
}
