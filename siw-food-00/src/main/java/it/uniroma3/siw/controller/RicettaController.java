package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.controller.validator.RicettaValidator;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.CuocoRepository;
import it.uniroma3.siw.repository.RicettaRepository;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.RicettaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;



@Controller
public class RicettaController {
	
	@Autowired RicettaService ricettaService;
	@Autowired RicettaRepository ricettaRepository;
	@Autowired CuocoService cuocoService;
	@Autowired CuocoRepository cuocoRepository;
	@Autowired RicettaValidator ricettaValidator;
	
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
	
	@GetMapping("/formNewRicetta")
	public String formNewRicetta(Model model) {
		Ricetta ricetta = new Ricetta();
		model.addAttribute("ricetta", ricetta);
		model.addAttribute("cuochi", this.cuocoService.findAll());
		return "formNewRicetta.html";
	}
	
	@PostMapping("ricetta")
	public String newRicetta(@Valid @ModelAttribute("ricetta") Ricetta ricetta, BindingResult bindingResult, Model model) {
		
		this.ricettaValidator.validate(ricetta, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.ricettaRepository.save(ricetta); 
			model.addAttribute("ricetta", ricetta);
			return "ricetta.html";
		} else {
			return "formNewRicetta.html"; 
		}
	}
}
