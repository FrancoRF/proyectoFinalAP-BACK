package com.example.demo.security.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.example.demo.entity.Acercade;
import com.example.demo.entity.Educacion;
import com.example.demo.entity.Experiencia;
import com.example.demo.entity.Habilidad;
import com.example.demo.entity.Perfil;
import com.example.demo.entity.Proyecto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	private String nombre;
	@NotNull
	@Column(unique = true)
	private String nombreUsuario;
	@NotNull
	private String email;
	@NotNull
	private String password;
	
	@NotNull
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinTable(name = "rol_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private Set<Rol> roles = new HashSet<>();
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("usuario")
	private Set<Perfil> perfil = new HashSet<>();

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("usuario")
	private Set<Acercade> acercade = new HashSet<>();
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("usuario")
	private Set<Educacion> educacion = new HashSet<>();
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("usuario")
	private Set<Experiencia> experiencia = new HashSet<>();
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("usuario")
	private Set<Habilidad> habilidad = new HashSet<>();

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("usuario")
	private Set<Proyecto> proyecto = new HashSet<>();

	public Usuario() {
	}

	public Usuario(@NotNull String nombre, @NotNull String nombreUsuario, @NotNull String email,
			@NotNull String password) {
		super();
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
	
	public Set<Perfil> getPerfil() {
		return perfil;
	}

	public void setPerfil(Set<Perfil> perfil) {
		this.perfil = perfil;
	}
	
	public Set<Acercade> getAcercade() {
		return acercade;
	}

	public void setAcercade(Set<Acercade> acercade) {
		this.acercade = acercade;
	}
	
	public Set<Educacion> getEducacion() {
		return educacion;
	}

	public void setEducacion(Set<Educacion> educacion) {
		this.educacion = educacion;
	}

	public Set<Experiencia> getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(Set<Experiencia> experiencia) {
		this.experiencia = experiencia;
	}
	
	public Set<Habilidad> getHabilidad() {
		return habilidad;
	}

	public void setHabilidad(Set<Habilidad> habilidad) {
		this.habilidad = habilidad;
	}
	
	public Set<Proyecto> getProyecto() {
		return proyecto;
	}

	public void setProyecto(Set<Proyecto> proyecto) {
		this.proyecto = proyecto;
	}

}
