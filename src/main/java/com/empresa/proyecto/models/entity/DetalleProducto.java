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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name = "detalle_producto") 
public class DetalleProducto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id; 
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	private Color color; 
	
	private Integer stock; 
	 
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	private Talla talla;

	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {})
	@JsonIgnore
	private Producto producto;  
	
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonIgnore
	@JoinColumn(name = "detalle_producto_id")
	private List<Bolsa> bolsa; 
 
	@Transient
	private String nombre_producto; 
	
	@Transient
	private Long id_producto; 
	
	
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
		if(this.producto!=null) {
			return this.producto.getNombre();
		}
		return this.nombre_producto; 
	}

	
	public List<Bolsa> getBolsa() {
		return bolsa;
	}

	public void setBolsa(List<Bolsa> bolsa) {
		this.bolsa = bolsa;
	}

	public void setNombre_producto(String nombre_producto) {
		this.nombre_producto = nombre_producto;
	}

	public Long getId_producto() {
		if(this.producto!=null) {
			return this.producto.getId();
		}
		return this.id_producto; 
	}

	public void setId_producto(Long id_producto) {
		this.id_producto = id_producto;
	}

 


	
	
	
	
	
	
	
}
