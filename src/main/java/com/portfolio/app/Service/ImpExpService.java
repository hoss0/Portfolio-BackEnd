package com.portfolio.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.app.Interface.IExpService;
import com.portfolio.app.model.entities.Exp;
import com.portfolio.app.repositories.IExpRepository;
@Service
public class ImpExpService implements IExpService{

	@Autowired IExpRepository iExpRepository;
	
	@Override
	public List<Exp> getList() {
		List<Exp> lista = iExpRepository.findAll();
		return lista;
	}

	@Override
	public void save(Exp exp) {
		iExpRepository.save(exp);
		
	}

	@Override
	public Exp find(Long id) {
		Exp exp = iExpRepository.findById(id).orElse(null);
		return exp;
	}

	@Override
	public void edit(Long id, String titulo_empresa, String titulo_puesto, String descripcion, String periodo) {
		Exp exp = iExpRepository.findById(id).orElse(null);
		exp.setTitulo_empresa(titulo_empresa);
		exp.setTitulo_puesto(titulo_puesto);
		exp.setDescripcion(descripcion);
		exp.setPeriodo(periodo);
		iExpRepository.save(exp);

	}

	@Override
	public boolean existById(Long id) {
		boolean exist = iExpRepository.existsById(id);
		return exist;
	}

	@Override
	public void deleteById(Long id) {
		iExpRepository.deleteById(id);

		
	}

}
