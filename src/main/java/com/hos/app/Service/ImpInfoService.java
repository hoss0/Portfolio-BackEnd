package com.hos.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hos.app.Entities.Info;
import com.hos.app.Interface.IInfoService;
import com.hos.app.Repositories.IInfoRepository;
@Service
public class ImpInfoService implements IInfoService {

	@Autowired IInfoRepository iInfoRepository;
	
	@Override
	public List<Info> getList() {
		List<Info> lista = iInfoRepository.findAll();
		return lista;
	}

	@Override
	public void save(Info info) {
		iInfoRepository.save(info);
		
	}

	@Override
	public Info find(Long id) {
		Info info = iInfoRepository.findById(id).orElse(null);
		return info;
	}

	@Override
	public void edit(Long id, String nombrecompleto, String titulo, String descripcion, byte[] imagen, String urlbanner) {
		Info info = iInfoRepository.findById(id).orElse(null);
		info.setNombrecompleto(nombrecompleto);
		info.setTitulo(titulo);
		info.setDescripcion(descripcion);
		info.setImagen(imagen);
		info.setUrlbanner(urlbanner);
		
		iInfoRepository.save(info);
	}

	@Override
	public boolean existById(Long id) {
		boolean exist = iInfoRepository.existsById(id);
		return exist;
	}

	@Override
	public void deleteById(Long id) {
		iInfoRepository.deleteById(id);		
		
	}

	
	
	
	
}
