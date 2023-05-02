package com.empresa.proyecto.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true,length = 20) 
	@NotNull(message = "El nombbre de usuario es requerido")
	@Length(min = 3,message = "El nombre de usuario debe tener 3 caracteres")
	@Length(max = 15,message = "El nombre de usuario debe tener menos de 15 caracteres")

	private String username; 
	
	@Column(length = 60)
	@NotNull(message = "La contraseña es requerida")
	@Length(min = 8,message = "La contraseña debe tener un mínimo de 8 caracteres")
	private String password; 
	private boolean enabled; 
	
	@NotNull(message = "El nombre es requerido")
	//@Length(min = 3,message = "El nombre debe tener 3 caracteres")
	@Length(max = 15,message = "El nombre debe tener menos de 20 caracteres")
	@Pattern(regexp = "^[-a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+(?:\\W+[-a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+){0,3}(?:\\W+[-\\s[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]]+)?$",
	message = "El nombre debe tener solo letras y al menos 3 caracteres")
	private String nombre; 	
	
	
	@NotNull(message = "El apellido es requerido")
	@Length(max = 15,message = "El nombre de usuario debe tener menos de 20 caracteres")
	@Pattern(regexp = "^[-a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+(?:\\W+[-a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+){0,5}(?:\\W+[-\\s[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]]+)?$",
			message = "El apellido debe tener solo letras")
	private String apellido;
	
	@NotNull(message = "El correo es requerido")
	@Column(unique = true) 
	@Email(message = "El email es inválido")
	private String email;
	 
	  
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL) //crea tabla intermedia usuario_roles
	@JsonIgnore
	@JoinTable(name = "usuario_roles", 
					joinColumns = @JoinColumn(name="usuario_id"),
					//usuario_id y role_id no se puede repetir ese conjunto	
					uniqueConstraints =  {@UniqueConstraint(columnNames = {"usuario_id","role_id"})} , //para que solo pueda tener 1 vez 1 rol
					inverseJoinColumns = @JoinColumn(name="role_id")) //estyo solo si quiueremos cambiar el name
	private List<Role> roles;

	
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL) 
	@JoinColumn(name = "usuario_id")
	private List<Bolsa> bolsa;  
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL) 
	@JoinColumn(name = "usuario_id")
	private List<Compra> compras;  
	
	
	public Usuario() {
	}

	public Long getId() {
		return id; 
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
 
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}
 
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email; 
	}

	public void setEmail(String email) {
		this.email = email; 
	}
	
	

	public List<Bolsa> getBolsa() {
		return bolsa;
	}

	public void setBolsa(List<Bolsa> bolsa) {
		this.bolsa = bolsa;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", password=" + password + ", enabled=" + enabled
				+ ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", roles=" + roles
				+ ", productos=" + bolsa + ", compras=" + compras + "]";
	}
	
	



	
	
	
	
	
}


