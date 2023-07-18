package jpa.io.entity;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class hoaDon {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int maHD;
	private Date date;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id",referencedColumnName = "id")
	private Users user; 
	@OneToMany(mappedBy =  "maHoaDon",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<chiTietHoaDon> chiTietHoasDons;
	public hoaDon() {
		
	}
	public hoaDon( Date date) {
		super();
		//this.maHD = maHD;
		this.date = date;
	}
	public int getMaHD() {
		return maHD;
	}
	public void setMaHD(int maHD) {
		this.maHD = maHD;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<chiTietHoaDon> getChiTietHoasDons() {
		return chiTietHoasDons;
	}
	public void setChiTietHoasDons(List<chiTietHoaDon> chiTietHoasDons) {
		this.chiTietHoasDons = chiTietHoasDons;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	
}
