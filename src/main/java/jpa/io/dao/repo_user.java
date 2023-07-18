package jpa.io.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.io.entity.Users;

public interface repo_user extends JpaRepository<Users, Integer>{
	Users  findByUsername(String username);
}
