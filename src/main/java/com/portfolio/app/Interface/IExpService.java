package com.portfolio.app.Interface;

import java.util.List;

import com.portfolio.app.model.entities.Exp;

public interface IExpService {
	public List<Exp> getList();
	public void save(Exp exp);
	public Exp find (Long id);
	public void edit(Long id, String titulo_empresa, String titulo_puesto, String descripcion, String periodo);
	public boolean existById(Long id);
	public void deleteById(Long id);
}
