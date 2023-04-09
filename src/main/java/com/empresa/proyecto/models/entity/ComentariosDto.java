package com.empresa.proyecto.models.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class ComentariosDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long Id;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Producto producto; 
	
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Usuario usuario;  
	
	
	@NotNull
	private String comentario;
	
	
	private Double valoracion;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

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

	@Override
	public String toString() {
		return "ComentariosDto [Id=" + Id + ", producto=" + producto + ", usuario=" + usuario + ", comentario="
				+ comentario + ", valoracion=" + valoracion + "]";
	} 
	
	
	
	
	
}
