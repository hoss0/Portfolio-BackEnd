package com.portfolio.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.app.Interface.IInfoService;
import com.portfolio.app.model.entities.Info;
import com.portfolio.app.repositories.IInfoRepository;
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
