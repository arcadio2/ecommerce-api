package com.empresa.proyecto.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "comentarios")
public class Comentarios implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id; 
	
	//@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Producto producto; 
	
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	//@JsonIgnore
	private Usuario usuario;  
	
	@Transient
	private String username; 
	 
	 
	private String comentario;
 

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


	public String getUsername() {
		if(this.getUsuario()!=null) {
			this.username = this.getUsuario().getUsername();
		}
		return this.username;
	}


	public void setUsername(String username) {
		this.username = username;
		if(this.getUsername()!=null) {
			this.username = this.getUsuario().getUsername();
		}
		
	}


	@Override
	public String toString() {
		return "Comentarios [producto=" + producto + ", usuario=" + usuario + ", username=" + username + ", comentario="
				+ comentario + "]";
	} 
	
	
	
	

}
