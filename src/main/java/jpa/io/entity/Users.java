package jpa.io.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity

public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String username;
	private String pass;
	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonManagedReference
	private hoaDon hoadon;
	public Users() {
		super();
	}
	public Users( String username, String pass) {
		super();
		this.username = username;
		this.pass = pass;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public hoaDon getHoadon() {
		return hoadon;
	}
	public void setHoadon(hoaDon hoadon) {
		this.hoadon = hoadon;
	}

	
	
}
