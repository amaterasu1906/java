package com.forms.web.app.models;

import java.util.Date;
import java.util.List;

//import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//import org.springframework.format.annotation.DateTimeFormat;

import com.forms.web.app.validators.anotaciones.ValidacionId;

public class Usuario {
	
//	@Pattern(regexp = "[0-9]{2}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}")
	@ValidacionId
	private String id;
	@NotEmpty
	@Size(min = 3, max=15)
	private String nombre;
//	@NotEmpty
	private String apellido;	
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@NotEmpty
	@Email
	private String email;
	@NotNull
	@Min(2)
	@Max(1000)
	private Integer cuenta;
	@NotNull
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Future
	private Date fechaNacimiento;
	@NotNull
	private Pais pais;
	@NotEmpty
	private List<Role> roles;
	
	private Boolean habilitado;
	@NotEmpty
	private String genero;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getCuenta() {
		return cuenta;
	}
	public void setCuenta(Integer cuenta) {
		this.cuenta = cuenta;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Boolean getHabilitado() {
		return habilitado;
	}
	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}

}
