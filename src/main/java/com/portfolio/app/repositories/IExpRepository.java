package com.portfolio.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.app.model.entities.Exp;

public interface IExpRepository extends JpaRepository<Exp, Long> {

}
