package jpa.io.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(chiTietHoaDonkey.class)
public class chiTietHoaDon {
	@Id
	@ManyToOne
	@JoinColumn(name = "maHD",referencedColumnName = "maHD")
	private hoaDon maHoaDon;
	@Id
	@ManyToOne
	@JoinColumn(name="masp",referencedColumnName = "maSP")
	private product maSanPham;
	private int sl;
	public chiTietHoaDon() {
		
	}
	public chiTietHoaDon(int sl) {
		super();
		this.sl = sl;
	}
	public hoaDon getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(hoaDon maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public product getSp() {
		return maSanPham;
	}
	public void setSp(product sp) {
		this.maSanPham = sp;
	}
	public int getSl() {
		return sl;
	}
	public void setSl(int sl) {
		this.sl = sl;
	}
	
}
