package com.portfolio.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.app.model.entities.User;

public interface IUserRepository extends JpaRepository<User, Long> {
	boolean existsByUsername(String username);

	void deleteByUsername(String username);
}