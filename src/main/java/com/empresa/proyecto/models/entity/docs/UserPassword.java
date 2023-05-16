package com.empresa.proyecto.models.entity.docs;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UserPassword implements Serializable{
	
	@NotNull(message = "La contraseña es requerida")
	@Length(min = 8,message = "La contraseña debe tener un mínimo de 8 caracteres")
	private String contrasenia;
	
	private String vieja; 
	
	

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getVieja() {
		return vieja;
	}

	public void setVieja(String vieja) {
		this.vieja = vieja;
	} 

	
	
}
