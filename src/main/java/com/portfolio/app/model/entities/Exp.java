package com.portfolio.app.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
public class Exp {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String titulo_empresa;
	@NotNull
	private String titulo_puesto;
	@NotNull
	private String descripcion;
	@NotNull
	private String periodo;
	@ManyToOne
	@NotNull(message = "El ID de usuario no debe ser nulo")
    private User userid;
	
	
	public User getUserid() {
		return userid;
	}
	public void setUser_id(User userid) {
		this.userid = userid;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo_empresa() {
		return titulo_empresa;
	}
	public void setTitulo_empresa(String titulo_empresa) {
		this.titulo_empresa = titulo_empresa;
	}
	public String getTitulo_puesto() {
		return titulo_puesto;
	}
	public void setTitulo_puesto(String titulo_puesto) {
		this.titulo_puesto = titulo_puesto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	
	
}
