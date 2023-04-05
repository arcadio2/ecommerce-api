package com.empresa.proyecto.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "producto")
public class Producto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id; 
	
	private String nombre; 
	
	private String descripcion; 
	
	private Double precio; 
	 
	
	
	private Double valoracion_total; 
	
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL) 
	@JoinColumn(name = "producto_id")
	private List<Talla> tallas; 
	 
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	//@NotNull(message = "Ingrese una categoría") 
	private CategoriaProducto categoria; 
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL) 
	@JoinColumn(name = "producto_id")
	private List<Comentarios> comentarios; 
	
	 
	
	 
}
