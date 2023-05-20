package com.portfolio.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.app.Interface.IEducacionService;
import com.portfolio.app.model.entities.Educacion;
import com.portfolio.app.repositories.IEducacionRepository;
@Service
public class ImpEducacionService implements IEducacionService {

	@Autowired IEducacionRepository iEducacionRepository;
	
	@Override
	public List<Educacion> getList() {
		List<Educacion> lista= iEducacionRepository.findAll();
		return lista;
	}

	@Override
	public void save(Educacion edu) {
		iEducacionRepository.save(edu);
		
	}

	@Override
	public Educacion find(Long id) {
		Educacion educacion = iEducacionRepository.findById(id).orElse(null);
		return educacion;
	}

	@Override
	public void edit(Long id, String institucion, String titulo, String descripcion, String periodo) {
		Educacion edu = iEducacionRepository.findById(id).orElse(null);
		edu.setInstitucion(institucion);
		edu.setTitulo(titulo);
		edu.setDescripcion(descripcion);
		edu.setPeriodo(periodo);

		iEducacionRepository.save(edu);
		
	}

	@Override
	public boolean existById(Long id) {
		boolean exist = iEducacionRepository.existsById(id);
		return exist;
	}

	@Override
	public void deleteById(Long id) {
		iEducacionRepository.deleteById(id);
		
	}

}
