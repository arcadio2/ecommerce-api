package com.empresa.proyecto.models.entity;

import java.io.Serializable;

public class ComentarioValoracion implements Serializable{
	
	private String comentario; 
	
	private Double valoracion;
	
	private String titulo; 

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Double getValoracion() {
		return valoracion;
	}

	public void setValoracion(Double valoracion) {
		this.valoracion = valoracion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	} 

	
	 
}
