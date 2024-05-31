package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.controller.validator.RicettaValidator;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.CuocoRepository;
import it.uniroma3.siw.repository.IngredienteRepository;
import it.uniroma3.siw.repository.RicettaRepository;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.RicettaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;



@Controller
public class RicettaController {
	
	@Autowired RicettaService ricettaService;
	@Autowired RicettaRepository ricettaRepository;
	@Autowired CuocoService cuocoService;
	@Autowired CuocoRepository cuocoRepository;
	@Autowired RicettaValidator ricettaValidator;
	@Autowired IngredienteRepository ingredienteRepository;
	@Autowired CredentialsRepository credentialsRepository;
	
	@GetMapping("/ricette")
	public String mostraRicette(Model model) {
		model.addAttribute("ricette", this.ricettaService.findAll());
		return "ricette.html";
	}
	
	@GetMapping("/ricetta/{id}")
	public String getRicetta(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ricetta", this.ricettaService.findById(id));
		Ricetta ricetta = this.ricettaService.findById(id);
		if(ricetta.getCuoco()!=null) {
			model.addAttribute("creatore", ricetta.getCuoco());
		}
		return "ricetta.html";
	}
	
	@GetMapping(value = "/cuoco/formNewRicetta/{username}")
	public String formNewRicettaCuoco(@PathVariable("username") String username, Model model) {
		Credentials tempUser = credentialsRepository.findByUsername(username);
		User currentUser = tempUser.getUser();
		Cuoco currentCuoco = this.cuocoRepository.findByNameAndSurname(currentUser.getName(), currentUser.getSurname());
		Ricetta ricetta = new Ricetta();
		model.addAttribute("cuoco", currentCuoco);
		model.addAttribute("cuocoId", currentCuoco.getId());
		model.addAttribute("ricetta", ricetta);
		model.addAttribute("userDetails", tempUser); // Aggiungi userDetails al modello
		return "cuoco/formNewRicetta.html";
	}
	
	@GetMapping(value = "/admin/formNewRicetta")
	public String formNewRicetta(Model model) {
		model.addAttribute("ricetta", new Ricetta());
		return "/admin/formNewRicetta.html";
	}
	
	@PostMapping("/cuoco/ricetta")
	public String newRicettaCuoco(@Valid @ModelAttribute("ricetta") Ricetta ricetta, BindingResult bindingResult,
			@RequestParam("username") String username, Model model) {
		Credentials tempUser = credentialsRepository.findByUsername(username);
		User currentUser = tempUser.getUser();
		Cuoco currentCuoco = this.cuocoRepository.findByNameAndSurname(currentUser.getName(), currentUser.getSurname());
		ricetta.setCuoco(currentCuoco);

		this.ricettaValidator.validate(ricetta, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.ricettaRepository.save(ricetta);
			model.addAttribute("ricetta", ricetta);
			return "ricetta.html";
		} else {
			return "cuoco/formNewRicetta.html";
		}
	}
}
