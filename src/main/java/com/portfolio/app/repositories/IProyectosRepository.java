package com.portfolio.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.app.model.entities.Proyectos;

public interface IProyectosRepository extends JpaRepository<Proyectos, Long>{

}
