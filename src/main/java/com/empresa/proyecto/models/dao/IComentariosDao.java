package com.empresa.proyecto.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.empresa.proyecto.models.entity.Comentarios;

public interface IComentariosDao extends CrudRepository<Comentarios, Long>{

}
