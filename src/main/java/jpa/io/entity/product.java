package jpa.io.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class product {
	@Id
	private String masp;
	private String tensp;
	private double gia;
	private String url;
	@OneToMany(mappedBy = "maSanPham",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List< chiTietHoaDon> chiTietHoaDons;
	public product() {
		super();
	}
	public product(String masp, String tensp, double gia,String url) {
		super();
		this.masp = masp;
		this.tensp = tensp;
		this.gia = gia;
		this.url=url;
	}
	public String getMasp() {
		return masp;
	}
	public void setMasp(String masp) {
		this.masp = masp;
	}
	public String getTensp() {
		return tensp;
	}
	public void setTensp(String tensp) {
		this.tensp = tensp;
	}
	public double getGia() {
		return gia;
	}
	public void setGia(double gia) {
		this.gia = gia;
	}
	public List<chiTietHoaDon> getChiTietHoaDons() {
		return chiTietHoaDons;
	}
	public void setChiTietHoaDons(List<chiTietHoaDon> chiTietHoaDons) {
		this.chiTietHoaDons = chiTietHoaDons;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
