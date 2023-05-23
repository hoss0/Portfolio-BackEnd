package com.hos.app.Interface;

import java.util.List;

import com.hos.app.Entities.User;



public interface IUserService {

	
	public List<User> getList();
	public void saveUser(User user);
	public User findUser (Long id);
	public void editUser(Long id, String username, String pass);
	public boolean existByUsername(String username);
	public boolean existById(Long id);
	public void deleteById(Long id);
	public void addToken(User user, String token);
	
}
