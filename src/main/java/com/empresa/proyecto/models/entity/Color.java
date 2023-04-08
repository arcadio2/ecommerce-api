package com.empresa.proyecto.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "colores") 
public class Color implements Serializable{
	
	private static final long serialVersionUID = 1L;
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id; 
	
	private String color;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	} 

	
	
}
