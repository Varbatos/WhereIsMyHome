package com.ssafy.Member.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.Member.model.MemberDto;
import com.ssafy.Member.model.service.MemberService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	MemberService service;

	public MemberController(MemberService service) {
		this.service = service;
	}

	@RequestMapping({"/","index"})
	public String index() {
		return "index";
	}

	@PostMapping("/login")
	@ResponseBody
	public MemberDto login(@RequestParam("userId") String userId, @RequestParam("password") String password,
			HttpSession session, Model model) throws Exception {
		System.out.println(userId+" "+ password);
		MemberDto user = service.login(userId, password);
		user.setPassword(null);
		System.out.println(user);
		if (user != null && user.getName() != null) {
			return user;
		}
		throw new Exception("로그인 도중 에러가 발생하였습니다.");
	}

	@GetMapping("/join")
	public String join() {
		return "join";
	}

	@PostMapping("/join")
	@ResponseBody
	public void join(MemberDto user, Model model,HttpServletResponse response) {
		try {
			System.out.println(user);
			service.join(user);
			response.setStatus(200);
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(401);
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
	
	@GetMapping("/loginView")
	public String loginView() {
		return "loginView";
	}
}
