package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.demo.security.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Perfil implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private String nombre;
    private String titulo;
    private String ubicacion;
    private String email;
    
    private byte[] imagenPer;
    private String imgPerfil;
    private byte[] imagenPor;
    private String imgPortada;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nombre_usuario", referencedColumnName = "nombreUsuario")
    @JsonIgnoreProperties("perfil")
    private Usuario usuario;

	public Perfil() {
	}

	public Perfil(int id, String nombre, String titulo, String ubicacion, String email, byte[] imagenPer,
			String imgPerfil, byte[] imagenPor, String imgPortada) {
		this.id = id;
		this.nombre = nombre;
		this.titulo = titulo;
		this.ubicacion = ubicacion;
		this.email = email;
		this.imagenPer = imagenPer;
		this.imgPerfil = imgPerfil;
		this.imagenPor = imagenPor;
		this.imgPortada = imgPortada;
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getImagenPer() {
		return imagenPer;
	}

	public void setImagenPer(byte[] imagenPer) {
		this.imagenPer = imagenPer;
	}

	public String getImgPerfil() {
		return imgPerfil;
	}

	public void setImgPerfil(String imgPerfil) {
		this.imgPerfil = imgPerfil;
	}

	public byte[] getImagenPor() {
		return imagenPor;
	}

	public void setImagenPor(byte[] imagenPor) {
		this.imagenPor = imagenPor;
	}

	public String getImgPortada() {
		return imgPortada;
	}

	public void setImgPortada(String imgPortada) {
		this.imgPortada = imgPortada;
	}
	
	@JsonIgnore
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
