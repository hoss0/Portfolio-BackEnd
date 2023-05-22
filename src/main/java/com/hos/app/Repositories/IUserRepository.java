package com.hos.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hos.app.Entities.User;



public interface IUserRepository extends JpaRepository<User, Long> {
	boolean existsByUsername(String username);

	void deleteByUsername(String username);
}