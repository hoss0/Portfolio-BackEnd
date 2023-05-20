package com.portfolio.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.app.Interface.ISkillsService;
import com.portfolio.app.model.entities.Skills;
import com.portfolio.app.repositories.ISkillsRepository;
@Service
public class ImpSkillsService implements ISkillsService {
	
	@Autowired ISkillsRepository iSkillsRepository;
	
	@Override
	public List<Skills> getList() {
		List<Skills> lista = iSkillsRepository.findAll();
		return lista;
	}

	@Override
	public void save(Skills skill) {
		iSkillsRepository.save(skill);
		
	}

	@Override
	public Skills find(Long id) {
		Skills skill = iSkillsRepository.findById(id).orElse(null);
		return skill;
	}

	@Override
	public void edit(Long id, String nombre, Double cantidad) {
		Skills skill = iSkillsRepository.findById(id).orElse(null);
		skill.setCantidad(cantidad);
		skill.setNombre(nombre);
		iSkillsRepository.save(skill);
		
	}

	@Override
	public boolean existById(Long id) {
		boolean exist = iSkillsRepository.existsById(id);
		return exist;
	}

	@Override
	public void deleteById(Long id) {
		iSkillsRepository.deleteById(id);
		
	}

}
