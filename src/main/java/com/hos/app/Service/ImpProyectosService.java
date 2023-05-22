package com.hos.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hos.app.Entities.Proyectos;
import com.hos.app.Interface.IProyectosService;

import com.hos.app.Repositories.IProyectosRepository;
@Service
public class ImpProyectosService implements IProyectosService{

	@Autowired IProyectosRepository iProyectosRepository;
	@Override
	public List<Proyectos> getList() {
		List<Proyectos> lista = iProyectosRepository.findAll();
		return lista;
	}

	@Override
	public void save(Proyectos proyecto) {
		iProyectosRepository.save(proyecto);
		
	}

	@Override
	public Proyectos find(Long id) {
		Proyectos proyecto = iProyectosRepository.findById(id).orElse(null);
		return proyecto;
	}

	@Override
	public void edit(Long id, String titulo, String descripcion, String url) {
		Proyectos proyecto = iProyectosRepository.findById(id).orElse(null);
		proyecto.setDescripcion(descripcion);
		proyecto.setTitulo(titulo);
		proyecto.setUrl(url);
		iProyectosRepository.save(proyecto);
	}

	@Override
	public boolean existById(Long id) {
		boolean exist = iProyectosRepository.existsById(id);
		return exist;
	}

	@Override
	public void deleteById(Long id) {
		iProyectosRepository.deleteById(id);
		
	}

}
