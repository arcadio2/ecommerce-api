package com.empresa.proyecto.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "compras")
public class Compra implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long 	Id; 
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;  
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	private DetalleProducto detalle_producto; 
	
	private String codigo_seguimiento; 
	
	
	private boolean active; 
	
	@Temporal(TemporalType.DATE)
	private Date fecha_compra; 
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Direccion direccion; 
	
	private Integer cantidad; 
	
	private Double precio; 
	

	
	
	@PrePersist
	public void iniciarFecha() {
		this.fecha_compra = new Date(); 
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public DetalleProducto getDetalle_producto() {
		return detalle_producto;
	}

	public void setDetalle_producto(DetalleProducto detalle_producto) {
		this.detalle_producto = detalle_producto;
	}

	public String getCodigo_seguimiento() {
		return codigo_seguimiento;
	}

	public void setCodigo_seguimiento(String codigo_seguimiento) {
		this.codigo_seguimiento = codigo_seguimiento;
	}

	public Date getFecha_compra() {
		return fecha_compra;
	}

	public void setFecha_compra(Date fecha_compra) { 
		this.fecha_compra = fecha_compra;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	
	

}
