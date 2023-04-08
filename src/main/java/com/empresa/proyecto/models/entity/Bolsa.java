package com.empresa.proyecto.models.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "bolsa")
public class Bolsa {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;  
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	private DetalleProducto detalle_producto ; 
	 
	private Integer cantidad; 
	/*
	 * @Query("SELECT producto FROM ProductoCompra producto_compra INNER JOIN producto_compra.producto producto WHERE producto_compra.bolsa.id = 1")
    	List<Producto> getProductosByBolsaId(Long bolsaId);
	 * */

}
