package spring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	// chi can khoi tao trang co the truy cap
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/shop")
	public String shopPage(){
		return "shop";
	}
}
