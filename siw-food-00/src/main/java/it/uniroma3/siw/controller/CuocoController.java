package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.CuocoRepository;
import it.uniroma3.siw.repository.RicettaRepository;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.RicettaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CuocoController {
	
	@Autowired CuocoService cuocoService;
	@Autowired CuocoRepository cuocoRepository;
	@Autowired RicettaService ricettaService;
	private static String UPLOAD_DIR = "C:\\Users\\utente\\Desktop\\siw-food-ws\\siw-food-00\\src\\main\\resources\\static\\images";
	
	
	@GetMapping("/cuochi")
	public String mostraCuochi(Model model) {
		model.addAttribute("cuochi", this.cuocoService.findAll());
		return "cuochi.html";
	}
	
	@GetMapping("/cuoco/{id}")
	public String getCuochi(@PathVariable("id") Long id, Model model) {
		Cuoco cuoco = this.cuocoService.findById(id);
		model.addAttribute("cuoco", cuoco);
		return "cuoco.html";
	}
	
}
