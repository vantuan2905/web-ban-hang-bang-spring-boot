package jpa.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jpa.io.dao.CTHD_service;
import jpa.io.dao.HD_service;
import jpa.io.dao.pro_service;
import jpa.io.dao.user_service;
import jpa.io.entity.Users;
import jpa.io.entity.chiTietHoaDon;
import jpa.io.entity.chiTietHoaDonkey;
import jpa.io.entity.hoaDon;
import jpa.io.entity.product;

@Controller
@RequestMapping("/")
@SessionAttributes(names = {"hoadon","products"})
public class controller {
	@Autowired
	private user_service us;
	@Autowired
	private pro_service ps;
	@Autowired
	private CTHD_service cs;
	@Autowired
	private HD_service hs;
	@ModelAttribute
	public void init(Model  model) {
		Users user=new Users(  null, null);
		model.addAttribute("user",user);
	}
	@ModelAttribute("products")
	public List<chiTietHoaDon> inits() {
		List<chiTietHoaDon> ds=new ArrayList<>();
		return ds;
	}
	@ModelAttribute("hoadon")
	public hoaDon hoadon(HttpServletRequest req) {
		Calendar car=new GregorianCalendar();
		car.setTime(new Date());
		java.sql.Date ngay=new java.sql.Date(car.YEAR,car.MONTH, car.DAY_OF_MONTH);
		hoaDon hoadon=new hoaDon(ngay);
		//hoadon.setMaHD(10);
		//hoadon.setDate(ngay);
		System.out.println(hoadon.getMaHD());
		return hoadon;
	}
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@PostMapping("/authentication")
	public String login(Users u,Model model,HttpServletRequest req,HttpServletResponse res) {
		String action="";
		String remember=req.getParameter("write_cookies");
		Users user=us.findByUsername(u.getUsername());
		boolean check=BCrypt.checkpw(u.getPass(), user.getPass());
		if(check==true) {
				if(remember!=null&&remember.equals("true")) {
					Cookie id=new Cookie("id", String.valueOf(u.getId()));
					Cookie username=new Cookie("username", u.getUsername());
					Cookie pass=new Cookie("password", u.getPass());
					res.addCookie(username);res.addCookie(pass);res.addCookie(id);
				}
			return "home";
		}
		else {
			model.addAttribute("authen","username or password is wrong");
			return "index";
		}
	}
	@GetMapping("/sign_up")
	public String sign_up() {
		return "sign_up";
	}
	@PostMapping("/sign_up")
	public String sign_up_post(@RequestParam String username,@RequestParam String pass) {
		String p=BCrypt.hashpw(pass, BCrypt.gensalt(10));
		Users u=new Users( username, p);
		//.out.println(email);
		us.save(u);
		return "index";
	}
	@GetMapping("/thoi_trang")
	public String thoi_trang(Model model) {
		List<product> ds=ps.findProduct("fashion");
		model.addAttribute("page", "fashion");
		model.addAttribute("items",ds);
		return "products";
	}
	@GetMapping("/food")
	public String food(Model model) {
		List<product> ds=ps.findProduct("food");
		model.addAttribute("page", "food");
		model.addAttribute("items",ds);
		return "products";
	}
	@GetMapping("/sport")
	public String sport(Model model) {
		List<product> ds=ps.findProduct("sport");
		model.addAttribute("page", "sport");
		model.addAttribute("items",ds);
		return "products";
	}
	@GetMapping("/pet")
	public String pet(Model model) {
		List<product> ds=ps.findProduct("pet");
		model.addAttribute("page", "pet");
		model.addAttribute("items",ds);
		return "products";
	}
	@GetMapping("/add_to_cart")
	public String add_cart(HttpSession session,HttpServletRequest req,@RequestParam String masp,Model model) {
		String page=req.getParameter("page");
		hoaDon h=(hoaDon) session.getAttribute("hoadon");
		System.out.println(h.getMaHD()+"+++");
		if(h.getUser()==null) {
			Cookie[] cookie=req.getCookies();
			String username = null,pass = null,id = null;
			for(Cookie i:cookie) {
				if(i.getName().equals("username")) username=i.getValue();
				if(i.getName().equals("password")) pass=i.getValue();
				if(i.getName().equals("id")) id=i.getValue();
			}
			Users u=us.findByUsername(username);
			h.setUser(u);
			u.setHoadon(h);
			System.out.println(h.getMaHD());
		//	h.setMaHD(23);
			//hs.save(h);
		}
		List<chiTietHoaDon> ds=(List<chiTietHoaDon>) session.getAttribute("products");
		ds=cs.findCTHD(h.getMaHD());
		product p=ps.findProById(masp);
		chiTietHoaDon c=new chiTietHoaDon(1);
		c.setSp(p);c.setMaHoaDon(h);
		boolean add=true;
		for(int i=0;i<ds.size();i++) {
			if(ds.get(i).getSp().getMasp().equals(p.getMasp())) {
				ds.get(i).setSl(ds.get(i).getSl()+1);
				add=false;
			}
		}
		if(add) ds.add(c);
		System.out.println("  "+h.getMaHD()+ds.size()+" "+add);
		h.setChiTietHoasDons(ds);
		//h.setMaHD(2);
		//hs.delete(h);
		hs.save(h);
		System.out.println(h.getMaHD()+" "+h.getDate()+"--------"+masp+" "+ds.size());
		List<product> dss=ps.findProduct(page);
		model.addAttribute("page", page);
		model.addAttribute("items",dss);
		return "products";
	}
	@GetMapping("/xem_gio_hang")
	public String gio_hang(HttpSession session,Model model,HttpServletRequest req) {
		String page=req.getParameter("page");
		model.addAttribute("page",page);
		hoaDon h=(hoaDon) session.getAttribute("hoadon");
		int mahd=h.getMaHD();
		List<chiTietHoaDon> ds=cs.findCTHD(mahd);
		model.addAttribute("items",ds);
		double total=0;
		for(int i=0;i<ds.size();i++) {
			total+=ds.get(i).getSp().getGia()*ds.get(i).getSl();
		}
		model.addAttribute("total",(double) Math.round(total*10)/10);
		return "gio_hang";
	}
	@GetMapping("/change_sl")
	public String change_sl(HttpSession session,HttpServletRequest req,Model model) {
		String page=req.getParameter("page");
		model.addAttribute("page",page);
		String maHD=req.getParameter("maHD");
		String maSP=req.getParameter("maSP");
		String action=req.getParameter("action");
		cs.updateCTHD(maHD, maSP, action);
		hoaDon h=(hoaDon) session.getAttribute("hoadon");
		int mahd=h.getMaHD();
		List<chiTietHoaDon> ds=cs.findCTHD(mahd);
		model.addAttribute("items",ds);
		double total=0;
		for(int i=0;i<ds.size();i++) {
			total+=ds.get(i).getSp().getGia()*ds.get(i).getSl();
		}
		model.addAttribute("total",(double) Math.round(total*10)/10);
		return "gio_hang";
	}
	@GetMapping("/xoa")
	public String xoa_gio_hang(HttpSession session,Model model,HttpServletRequest req) {
		String page=req.getParameter("page");
		model.addAttribute("page",page);
		String maHD=req.getParameter("maHD");
		String maSP=req.getParameter("maSP");
		chiTietHoaDonkey id=new chiTietHoaDonkey(Integer.parseInt(maHD), maSP);
		cs.delete(id);
		hoaDon h=(hoaDon) session.getAttribute("hoadon");
		int mahd=h.getMaHD();
		List<chiTietHoaDon> ds=cs.findCTHD(mahd);
		h.setChiTietHoasDons(ds);
		hs.save(h);
		model.addAttribute("items",ds);
		double total=0;
		for(int i=0;i<ds.size();i++) {
			total+=ds.get(i).getSp().getGia()*ds.get(i).getSl();
		}
		model.addAttribute("total",(double) Math.round(total*10)/10);
		return "gio_hang";
	}
	@GetMapping("/quay_lai")
	public String quay_lai(@RequestParam String page,Model model) {
		List<product> dss=ps.findProduct(page);
		model.addAttribute("page", page);
		model.addAttribute("items",dss);
		return "products";
	}
	@GetMapping("/Add_product")
	public String Add_product() {
		return "AddFrm";
	}
	@PostMapping("/Add_product")
	public String Add_product_post(@RequestParam String masp,@RequestParam String tensp,@RequestParam String gia,@RequestParam MultipartFile url) throws IOException {
		Path path=Paths.get("C:\\Users\\admin\\Documents\\workspace-spring-tool-suite-4-4.18.0.RELEASE\\z_project001\\src\\main\\resources\\static\\images",url.getOriginalFilename());
		Files.write(path, url.getBytes());
		System.out.println(masp+" "+tensp+" "+gia+" "+ps.count(masp));
		masp=masp+(ps.count(masp)+1);
		ps.save(new product(masp, tensp, Double.parseDouble(gia),"images/"+url.getOriginalFilename()));
		return "home";
	}
	@GetMapping("/xoa_het")
	public String xoahet(HttpSession session) {
		hoaDon h=(hoaDon) session.getAttribute("hoadon");
		hs.delete(h);
		return "home";
	}
}
