package com.empresa.proyecto.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne; 
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "producto")
public class Producto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id; 

	@NotNull(message = "Debes ingresar un nombre")
	private String nombre; 
	 
	
	@NotNull(message = "Debes ingresar una descripción")
	@Column(length = 2000)
	private String descripcion; 
	
	@NotNull(message = "Debes ingresar un precio")
	private Double precio;  
	 
	
	private Double valoracion_total; 
	 
 
	
	@Temporal(TemporalType.DATE)
	private Date fecha_subida;
	 
	private boolean isHombre; 
	
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)  
	@JoinColumn(name = "producto_id")
	private List<DetalleProducto> detalle;  
	 
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	//@NotNull(message = "Ingrese una categoría") 
	private CategoriaProducto categoria; 
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL) 
	@JoinColumn(name = "producto_id")
	private List<Comentarios> comentarios; 
	/*
	 * 
	 * select talla,color,nombre from tallas_producto inner join 
	 * colores_producto on colores_producto.id=color_id inner join producto
	 *  on producto.id=producto_id where producto.nombre='playera verde';
	 * 
	 * */
	
	@PrePersist
	public void iniciarFecha() {
		this.fecha_subida = new Date(); 
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Double getValoracion_total() {
		return valoracion_total;
	}

	public void setValoracion_total(Double valoracion_total) {
		this.valoracion_total = valoracion_total;
	}

	public List<DetalleProducto> getDetalle() {
		return detalle;
	}

	public void setColores(List<DetalleProducto> detalle) {
		this.detalle = detalle;
	}

	public CategoriaProducto getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaProducto categoria) {
		this.categoria = categoria;
	}

	public List<Comentarios> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentarios> comentarios) {
		this.comentarios = comentarios;
	}

	public boolean isHombre() {
		return isHombre;
	}

	public void setHombre(boolean isHombre) {
		this.isHombre = isHombre;
	}

	@Override
	public String toString() {
		return "Producto [Id=" + Id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", valoracion_total=" + valoracion_total + ", fecha_subida=" + fecha_subida + ", isHombre=" + isHombre
				+ ", detalle=" + detalle + ", categoria=" + categoria + ", comentarios=" + comentarios + "]";
	}
	 
	
	
	 
}
