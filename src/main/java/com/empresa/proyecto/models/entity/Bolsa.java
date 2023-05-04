package com.empresa.proyecto.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "bolsa",
uniqueConstraints = {@UniqueConstraint(columnNames = {"detalle_producto_id", "usuario_id"})}
)
public class Bolsa {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;  
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@ManyToOne(fetch = FetchType.LAZY)

	private DetalleProducto detalle_producto ; 
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	
	private Usuario usuario ; 
	
	 
	private Integer cantidad; 
	/*
	 * @Query("SELECT producto FROM ProductoCompra producto_compra INNER JOIN producto_compra.producto producto WHERE producto_compra.bolsa.id = 1")
    	List<Producto> getProductosByBolsaId(Long bolsaId);
	 * */


	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}


	public DetalleProducto getDetalle_producto() {
		return detalle_producto;
	}


	public void setDetalle_producto(DetalleProducto detalle_producto) {
		this.detalle_producto = detalle_producto;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Integer getCantidad() {
		return cantidad;
	}


	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}


	@Override
	public String toString() {
		return "Bolsa [Id=" + Id + ", detalle_producto=" + detalle_producto + ", usuario=" + usuario + ", cantidad="
				+ cantidad + "]";
	}
	
	

}
