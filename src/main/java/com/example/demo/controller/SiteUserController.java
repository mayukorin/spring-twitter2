package com.example.demo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.example.demo.model.SiteUser;
import com.example.demo.service.SiteUserService;
import com.example.demo.service.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SiteUserController {

	private final SiteUserService siteUserService;


	@GetMapping("/login")
	public String login() {
		return "login";
	}


	@GetMapping("/user_register")
	public String register(@ModelAttribute("user") SiteUser user) {
		return "register";
	}

	@PostMapping("/user_register")
	public String userProcess(@Validated @ModelAttribute("user") SiteUser user,BindingResult result) {

		if (result.hasErrors()) {
			return "register";
		}

		siteUserService.insert(user);

		return "redirect:/login?register";

	}

	@GetMapping("/user_edit")
	public String editUser(@AuthenticationPrincipal UserDetailsImpl userDetail,Model model) {

		model.addAttribute("login_user", siteUserService.findUserById(userDetail.getSiteUser().getId()));

		return "userEdit";
	}

	@PostMapping("/user_edit_register")
	public String userEdit(@Validated @ModelAttribute("login_user") SiteUser user,BindingResult result,@RequestParam("password") String password,@AuthenticationPrincipal UserDetailsImpl userDetail) {


		if (result.hasErrors()) {

			return "userEdit";
		}



		siteUserService.update(user,password,userDetail.getSiteUser().getPassword());



		return "redirect:/logout";


	}

}
