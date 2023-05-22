package com.hos.app.Interface;

import java.util.List;

import com.hos.app.Entities.Educacion;




public interface IEducacionService {

		public List<Educacion> getList();
		public void save(Educacion edu);
		public Educacion find (Long id);
		public void edit(Long id, String institucion, String titulo, String descripcion, String periodo);
		public boolean existById(Long id);
		public void deleteById(Long id);
}
