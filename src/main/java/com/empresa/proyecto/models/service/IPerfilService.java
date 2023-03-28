package com.empresa.proyecto.models.service;

import java.util.List;

import com.empresa.proyecto.models.entity.Perfil;
import com.empresa.proyecto.models.entity.Sexo;

public interface IPerfilService {

	public Perfil getProfileByUsername(String username);
	public Perfil getProfileById(Long id);
	public Perfil save(Perfil perfil); 
	
	
	
	public Sexo getSexoById(Long id); 
	
	
}
