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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "perfil")
public class Perfil implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private static final long serialVersionUID = 1L;
	  
	@OneToOne 
	@NotNull(message = "Requieres de un usuario para guardar tu perfil")
	private Usuario usuario; 
	 
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "Ingrese un sexo") 
	private Sexo sexo; 
	
	
	private String foto;
	 
	@Min(value = 14,message = "No puedes ingresar una edad tan baja")
	@Max(value = 90, message = "No puede ingresar una edad tan alta")
	private Integer edad; 
	
	//@DecimalMin(value = "30",message = "No puedes ingresar un peso tan bajo")
	//@DecimalMax(value = "200",message = "No puedes ingresar un peso tan alto")
	//private Double peso;  
	
	@DecimalMin(value = "1.1",message = "No puedes ingresar una estatura tan baja en metros")
	@DecimalMax(value = "2.3",message = "No puedes ingresar una estatura tan alta en metros")
	private Double altura;
	
	@DecimalMin(value = "30",message = "No puedes ingresar una talla tan baja")
	@DecimalMax(value = "200",message = "No puedes ingresar una talla tan alto")
	private Double talla_camisa; 
	
	@DecimalMin(value = "18",message = "No puedes ingresar una talla tan baja")
	@DecimalMax(value = "100",message = "No puedes ingresar una talla tan alto")
	private Double talla_pantalon; 

	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name = "perfil_direcciones", joinColumns = @JoinColumn(name="perfil_id"),
					uniqueConstraints =  {@UniqueConstraint(columnNames = {"perfil_id","direccion_id"})} , 
					inverseJoinColumns = @JoinColumn(name="direccion_id"))
	private List<Direccion> direcciones; 
	
	//@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	//@ManyToOne(fetch = FetchType.LAZY)
	//private Bolsa bolsa; 
	
	
	
	 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	/*public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}*/

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Double getTalla_camisa() {
		return talla_camisa;
	}

	public void setTalla_camisa(Double talla_camisa) {
		this.talla_camisa = talla_camisa;
	}

	public Double getTalla_pantalon() {
		return talla_pantalon;
	}

	public void setTalla_pantalon(Double talla_pantalon) {
		this.talla_pantalon = talla_pantalon;
	}

	public List<Direccion> getDirecciones() {
		return direcciones;
	}

	public void setDirecciones(List<Direccion> direcciones) {
		this.direcciones = direcciones;
	}

	


	
	
	
	
	
}
