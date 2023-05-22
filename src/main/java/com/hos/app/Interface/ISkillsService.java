package com.hos.app.Interface;

import java.util.List;

import com.hos.app.Entities.Skills;





public interface ISkillsService {
	public List<Skills> getList();
	public void save(Skills skill);
	public Skills find (Long id);
	public void edit(Long id, String nombre, Double cantidad);
	public boolean existById(Long id);
	public void deleteById(Long id);
}
