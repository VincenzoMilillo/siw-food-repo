package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;

    @Autowired
	private UserService userService;
    
    @Autowired
    private CuocoService cuocoService;
    
    @GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "formLogin.html";
	}
    
    @GetMapping (value = "/login/error")
	public String showLoginErrorForm(Model model) {
		String messaggioErrore = new String("Username o password incorretti");
		model.addAttribute("messaggioErrore", messaggioErrore);
		return "formLogin.html";
	}
    
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        return "index.html";
    }
    
    @GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegister.html";
	}
    
    @PostMapping(value = {"/register"} )
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult userBindingResult, 
    						   @Valid @ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult, Model model) {
    	
        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            userService.saveUser(user);
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("user", user);
            Cuoco nuovoCuoco = new Cuoco();
            nuovoCuoco.name = user.getName();
            nuovoCuoco.surname = user.getSurname();
            nuovoCuoco.birthdate = user.getBirthdate();
            nuovoCuoco.photo = user.getPhoto();
            this.cuocoService.save(nuovoCuoco);
            return "registrationSuccessful.html";
        }
        return "formRegister.html";
    }
}
