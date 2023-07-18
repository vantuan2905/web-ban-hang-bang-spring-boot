package jpa.io.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jpa.io.entity.Users;
@Service
public class user_service {
	@Autowired
	public repo_user rs;
	public void save(Users u) {
		rs.save(u);
	}
	public Users findByUsername(String username) {
		return rs.findByUsername(username);
	}
}
