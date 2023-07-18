package jpa.io.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jpa.io.entity.product;

public interface repo_pro extends JpaRepository<product, String>{
	@Query(nativeQuery = true,value = "select * from product where masp like ?1%")
	List<product> findByString(String masp);
	@Query(nativeQuery = true,value="select count(*) from product where masp like ?1%")
	int count(String masp);
}
