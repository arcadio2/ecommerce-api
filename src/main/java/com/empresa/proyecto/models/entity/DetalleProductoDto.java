package com.empresa.proyecto.models.entity;

import java.io.Serializable;



import javax.persistence.Id;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class DetalleProductoDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	
	private Long Id; 
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Color color; 
	
	private Integer stock; 
	 
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Talla talla;
	
	
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Producto producto;
	
	
	private String nombre_producto;


	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	} 


	public Color getColor() {
		return color;
	}


	public void setColor(Color color) {
		this.color = color;
	}


	public Integer getStock() {
		return stock;
	}


	public void setStock(Integer stock) {
		this.stock = stock;
	}


	public Talla getTalla() {
		return talla;
	}


	public void setTalla(Talla talla) {
		this.talla = talla;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public String getNombre_producto() {
		return nombre_producto;
	}


	public void setNombre_producto(String nombre_producto) {
		this.nombre_producto = nombre_producto;
	} 
	
	
	
	

}
