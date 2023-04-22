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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "categoria_producto")
public class CategoriaProducto implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name = "categoria_sexo", 
	joinColumns = @JoinColumn(name="categoria_producto_id"),
	uniqueConstraints =  {@UniqueConstraint(columnNames = {"categoria_producto_id","sexo_id"})} , 
	inverseJoinColumns = @JoinColumn(name="sexo_id"))
	private List<Sexo> sexo; */
	 
	
	private boolean isHombre; 
	private boolean isMujer; 
	
	
	private String tipo;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}




	public boolean isHombre() {
		return isHombre;
	}


	public void setHombre(boolean isHombre) {
		this.isHombre = isHombre;
	}


	public boolean isMujer() {  
		return isMujer;
	}


	public void setMujer(boolean isMujer) {
		this.isMujer = isMujer;
	}
	
	


	
	
	
	
	
}
