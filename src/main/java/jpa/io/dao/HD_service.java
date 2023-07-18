package jpa.io.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jpa.io.entity.hoaDon;
@Service
public class HD_service {
	@Autowired
	private repo_hoadon r;
	public void save(hoaDon h) {
		r.save(h);
	}
	public void delete(hoaDon h) {
		r.deleteById(h.getMaHD());;
	}
}
