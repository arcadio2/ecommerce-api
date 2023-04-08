package com.empresa.proyecto.models.entity;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "valoracion_producto")
public class Valoracion implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id; 
	

	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	private Producto producto; 
	
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	//@Column(unique = true)
	private Usuario usuario;  
	
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

	public Double getValoracion() {
		return valoracion;
	}

	public void setValoracion(Double valoracion) {
		this.valoracion = valoracion;
	}

	@Override
	public String toString() {
		return "Valoracion [Id=" + Id + ", producto=" + producto + ", usuario=" + usuario + ", valoracion=" + valoracion
				+ "]";
	} 
	
	
	

}
