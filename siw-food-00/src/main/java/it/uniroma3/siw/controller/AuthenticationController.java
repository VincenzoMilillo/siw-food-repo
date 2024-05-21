package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;

    @Autowired
	private UserService userService;
    
    @GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "formLogin.html";
	}
    
}
