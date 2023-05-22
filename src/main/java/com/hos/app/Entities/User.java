package com.hos.app.Entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "\\S+", message = "El nombre de usuario no debe contener espacios")
    @Size(min = 1, max = 20, message = "El nombre de usuario debe tener al menos 1 letra y ser menor a 20 letras")
    @NotNull(message = "El nombre de usuario no debe ser nulo")
    private String username;
    @Pattern(regexp = "\\S+", message = "La contraseña de usuario no debe contener espacios")
    @Size(min = 1, max = 20, message = "La contraseña de usuario debe tener al menos 1 letra y ser menor a 20 letras")
    @NotNull(message = "La contraseña de usuario no debe ser nulo")
    private String pass;
    
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
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
    

}