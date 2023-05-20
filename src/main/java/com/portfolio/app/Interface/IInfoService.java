package com.portfolio.app.Interface;

import java.util.List;

import com.portfolio.app.model.entities.Info;



public interface IInfoService {

	
	public List<Info> getList();
	public void save(Info info);
	public Info find (Long id);
	public void edit(Long id, String nombrecompleto, String titulo, String descripcion, byte[] imagen, String urlbanner);
	public boolean existById(Long id);
	public void deleteById(Long id);
	
}
