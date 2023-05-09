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
	
	private Integer num_int; 
	
	private Integer num_ext; 
	
	
	private String coloniaID;
	private Integer cp; 
	
	private String colonia; 
    private String estadoID;
    private String municipioID;

    private String calle; 
    private String numero_contacto; 
    
    private String info_adicional; 
	
	
	
	

}
