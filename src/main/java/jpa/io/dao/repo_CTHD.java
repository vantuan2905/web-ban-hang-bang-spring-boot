package jpa.io.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import jpa.io.entity.chiTietHoaDon;
import jpa.io.entity.chiTietHoaDonkey;

public interface repo_CTHD extends JpaRepository<chiTietHoaDon, chiTietHoaDonkey>{
	@Query(nativeQuery = true,value="select * from chi_tiet_hoa_don where mahd=?1")
	List<chiTietHoaDon> findChi_tietByMa(int mahd);
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value="update chi_tiet_hoa_don set sl=sl+1 where mahd=?1 and masp=?2")
	void tang_sl(String mahd,String masp);
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value="update chi_tiet_hoa_don set sl=sl-1 where mahd=?1 and masp=?2")
	void giam_sl(String mahd,String masp);
}
