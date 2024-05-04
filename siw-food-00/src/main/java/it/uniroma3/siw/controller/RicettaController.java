package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.CuocoRepository;
import it.uniroma3.siw.repository.RicettaRepository;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.RicettaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class RicettaController {
	
	@Autowired RicettaService ricettaService;
	@Autowired RicettaRepository ricettaRepository;
	@Autowired CuocoService cuocoService;
	@Autowired CuocoRepository cuocoRepository;
	
	@GetMapping("/ricette")
	public String mostraRicette(Model model) {
		model.addAttribute("ricette", this.ricettaService.findAll());
		return "ricette.html";
	}
	
	@GetMapping("/ricetta/{id}")
	public String getRicetta(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ricetta", this.ricettaService.findById(id));
		Ricetta ricetta = this.ricettaService.findById(id);
		if(ricetta.getCreatore()!=null) {
			model.addAttribute("creatore", ricetta.getCreatore());
		}
		return "ricetta.html";
	}
	
}
