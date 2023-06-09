package com.hos.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hos.app.Entities.User;
import com.hos.app.Interface.IUserService;
import com.hos.app.Repositories.IUserRepository;

@Service
public class ImpUserService implements IUserService {

	@Autowired IUserRepository iUserRepository;
	
	@Override
	public List<User> getList() {
		List<User> lista = iUserRepository.findAll();
		return lista;
	}

	@Override
	public void saveUser(User user) {
		iUserRepository.save(user);
		
	}

	@Override
	public void deleteById(Long id) {
		iUserRepository.deleteById(id);
		
	}

	@Override
	public User findUser(Long id) {
		User user = iUserRepository.findById(id).orElse(null);
		return user;
	}

	@Override
	public void editUser(Long id, String username, String pass) {
		User user = iUserRepository.findById(id).orElse(null);
		user.setUsername(username);
		user.setPass(pass);
		iUserRepository.save(user);
	}

	@Override
	public boolean existByUsername(String username) {
		boolean exist = iUserRepository.existsByUsername(username);
		return exist;
	}

	@Override
	public boolean existById(Long id) {
		boolean exist = iUserRepository.existsById(id);
		return exist;
	}

	@Override
	public void addToken(User user, String token) {
		user.setToken(token);
		
	}

}
