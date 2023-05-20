package com.portfolio.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.app.model.entities.Skills;

public interface ISkillsRepository extends JpaRepository<Skills, Long>{

}
