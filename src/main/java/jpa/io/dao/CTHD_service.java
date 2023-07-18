package jpa.io.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jpa.io.entity.chiTietHoaDon;
import jpa.io.entity.chiTietHoaDonkey;
@Service
public class CTHD_service {
	@Autowired
	private repo_CTHD r;
	public void save(chiTietHoaDon a) {
		r.save(a);
	}
	public List<chiTietHoaDon> findCTHD(int mahd){
		return r.findChi_tietByMa(mahd);
	}
	public void updateCTHD(String mahd,String masp,String action) {
		if(action.equals("tang")) {
			r.tang_sl(mahd, masp);
		}else r.giam_sl(mahd, masp);
	}
	public void delete(chiTietHoaDonkey id) {
		r.deleteById(id);
	}
}
