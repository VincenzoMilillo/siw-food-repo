package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.repository.IngredienteRepository;
import it.uniroma3.siw.service.IngredienteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class IngredienteController {
	
	@Autowired IngredienteService ingredienteService;
	@Autowired IngredienteRepository ingredienteRepository;
	
	@GetMapping("/ingredienti")
	public String mostraIngredienti(Model model) {
		model.addAttribute("ingredienti", this.ingredienteService.findAll());
		return "ingredienti.html";
	}
	
	@GetMapping("/ingrediente/{id}")
	public String getIngrediente(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = this.ingredienteService.findById(id);
		model.addAttribute("ingrediente", ingrediente);
		return "ingrediente.html";
	}
	
	@GetMapping(value="/cuoco/formNewIngrediente")
	public String formNewIngredienteCuoco(Model model) {
	    model.addAttribute("ingrediente", new Ingrediente());
		return "cuoco/formNewIngrediente.html";
	}
	
	@PostMapping("/cuoco/ingrediente")
	public String newIngredienteCuoco(@ModelAttribute("ingrediente") Ingrediente ingrediente, Model model) {
		if (!ingredienteRepository.existsByName(ingrediente.getName())) {
			this.ingredienteService.save(ingrediente);
			model.addAttribute("ingrediente", ingrediente);
			return "ingrediente.html";
		} else {
			model.addAttribute("messaggioErrore", "Questo ingrediente esiste già");
			return "/cuoco/formNewIngrediente.html";
		}
	}
	
	
}
