package com.myblog.myblog.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myblog.myblog.Service.UserService;
import com.myblog.myblog.entity.User;

@Controller
@RequestMapping("/admin")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/login")
	public String login() {
		return "admin/login";
	}
	
	@GetMapping("/index")
	public String index(HttpSession session, Model model) {
		
		return "admin/index";	
	}
	
	
	
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes, Model model) {
		User user = userService.checkuser(username, password);
		if(user != null) {
			user.setPassword(null);
			session.setAttribute("user", user);
			model.addAttribute("nickname", user.getNickname());
			return "admin/index";
		}else {
			attributes.addFlashAttribute("message", "Username or Password is wrong");
			return "redirect: /admin";
		}
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect: /admin";
	}

}
