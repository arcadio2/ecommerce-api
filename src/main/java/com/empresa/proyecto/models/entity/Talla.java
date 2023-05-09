package com.empresa.proyecto.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "tallas") 
public class Talla implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id; 
	
	private String talla;


	private boolean tronco_superior; 
	
	
	public Long getId() {
		return Id;  
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}
	 

	public boolean isTronco_superior() {
		return tronco_superior;
	}

	public void SetTronco_superior(boolean troncoSuperior) {
		this.tronco_superior = troncoSuperior;
	}

	@Override
	public String toString() {
		return "Talla [Id=" + Id + ", talla=" + talla + "]";
	} 
	
	
	
}
