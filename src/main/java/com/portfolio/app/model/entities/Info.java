package com.portfolio.app.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Info {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String nombrecompleto;
	@NotNull
	private String titulo;
	@NotNull
	private String descripcion;
	@NotNull
	@Lob
    private byte[] imagen;
	@NotNull
	private String urlbanner;
	@OneToOne
	@NotNull(message = "El ID de usuario no debe ser nulo")
    private User userid;
	
	
	public Long getId() {
		return id;
	}
	public String getUrlbanner() {
		return urlbanner;
	}
	public void setUrlbanner(String urlbanner) {
		this.urlbanner = urlbanner;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombrecompleto() {
		return nombrecompleto;
	}
	public void setNombrecompleto(String nombrecompleto) {
		this.nombrecompleto = nombrecompleto;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte[] getImagen() {
		return imagen;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	public User getUserid() {
		return userid;
	}
	public void setUserid(User userid) {
		this.userid = userid;
	}
	
	
	
}
