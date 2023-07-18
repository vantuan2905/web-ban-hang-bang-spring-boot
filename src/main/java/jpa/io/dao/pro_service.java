package jpa.io.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jpa.io.entity.Users;
import jpa.io.entity.product;

@Service
public class pro_service {
	@Autowired
	repo_pro r;
	public List<product> findAllProduct() {
		return r.findAll();
	}
	public List<product> findProduct(String masp) {
		return r.findByString(masp);
	}
	public product findProById(String masp) {
		return r.findById(masp).get();
	}
	public void save(product p) {
		r.save(p);
	}
	public int count(String masp) {
		return r.count(masp);
	}
}
