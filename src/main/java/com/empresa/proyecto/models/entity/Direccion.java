package com.empresa.proyecto.models.entity;

import java.io.Serializable;

import javax.persistence.*; 


@Entity
@Table(name = "direccion")
public class Direccion implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id; 
	
	
	
	private String municipio; 
	
	private String estado; 
	
	private Integer num_int; 
	
	private Integer num_ext; 
	
	private Integer cp; 
	
	private String colonia; 


    private String calle; 
    private String numero_contacto; 
    
    private String info_adicional;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getNum_int() {
		return num_int;
	}

	public void setNum_int(Integer num_int) {
		this.num_int = num_int;
	}

	public Integer getNum_ext() {
		return num_ext;
	}

	public void setNum_ext(Integer num_ext) {
		this.num_ext = num_ext;
	}

	public Integer getCp() {
		return cp;
	}

	public void setCp(Integer cp) {
		this.cp = cp;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumero_contacto() {
		return numero_contacto;
	}

	public void setNumero_contacto(String numero_contacto) {
		this.numero_contacto = numero_contacto;
	}

	public String getInfo_adicional() {
		return info_adicional;
	}

	public void setInfo_adicional(String info_adicional) {
		this.info_adicional = info_adicional;
	} 
	
	
	
	

}
