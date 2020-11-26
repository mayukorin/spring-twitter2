package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.SiteUser;

public interface UserRepository extends JpaRepository<SiteUser,Long> {

	SiteUser findByName(String name);

	boolean existsByName(String name);

}
