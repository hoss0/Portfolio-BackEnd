package com.portfolio.app.Interface;

import java.util.List;

import com.portfolio.app.model.entities.Proyectos;



public interface IProyectosService {
	public List<Proyectos> getList();
	public void save(Proyectos proyecto);
	public Proyectos find (Long id);
	public void edit(Long id, String titulo,String descripcion, String url);
	public boolean existById(Long id);
	public void deleteById(Long id);
}
