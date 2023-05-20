package com.portfolio.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.app.model.entities.Info;

public interface IInfoRepository extends JpaRepository<Info, Long>{

}
