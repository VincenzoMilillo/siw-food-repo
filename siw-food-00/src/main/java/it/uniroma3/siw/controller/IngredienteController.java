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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;




@Controller
public class IngredienteController {
	
	@Autowired IngredienteService ingredienteService;
	@Autowired IngredienteRepository ingredienteRepository;
	private static String UPLOAD_DIR = "C:\\Users\\utente\\Desktop\\siw-food-ws\\siw-food-00\\src\\main\\resources\\static\\images";
	
	
	/*PARTE DI CONTOLLER RELATIVA ALL'UTENTE CASUALE*/
	
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
	
	/*PARTE DI CONTOLLER RELATIVA AL CUOCO*/
	
	@GetMapping(value="/cuoco/formNewIngrediente")
	public String formNewIngredienteCuoco(Model model) {
	    model.addAttribute("ingrediente", new Ingrediente());
		return "cuoco/formNewIngrediente.html";
	}
	
	@PostMapping("/cuoco/ingrediente")
	public String newIngredienteCuoco(@ModelAttribute("ingrediente") Ingrediente ingrediente, 
            @RequestParam("immagine") MultipartFile file, Model model) {
		if (!ingredienteRepository.existsByName(ingrediente.getName())) {
	        if (!file.isEmpty()) {
	            try {
	                // carica il file sul DB
	                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	                Path path = Paths.get(UPLOAD_DIR + File.separator + fileName);
	                Files.write(path, file.getBytes());
	                ingrediente.setPhoto(fileName);

	                // Salva l'oggetto immagine sul DB
	                this.ingredienteService.save(ingrediente);
	                model.addAttribute("ingrediente", ingrediente);
	                return "ingrediente.html";
	            } catch (IOException e) {
	                e.printStackTrace();
	                model.addAttribute("messaggioErrore", "Errore caricamento dell'immagine");
	                return "/cuoco/formNewIngrediente.html";
	            }
	        } else {
	            model.addAttribute("messaggioErrore", "immagine vuota...");
	            return "/cuoco/formNewIngrediente.html";
	        }
	    } else {
			model.addAttribute("messaggioErrore", "Questo ingrediente esiste già");
			return "/cuoco/formNewIngrediente.html";
		}
	}
	
	/*PARTE DI CONTOLLER RELATIVA ALL'ADMIN*/
	
	@GetMapping(value = "/admin/formNewIngrediente")
	public String formNewIngrediente(Model model) {
		model.addAttribute("ingrediente", new Ingrediente());
		return "/admin/formNewIngrediente.html";
	}
	
	@PostMapping("/admin/ingrediente")
	public String newIngrediente(@ModelAttribute("ingrediente") Ingrediente ingrediente, 
	                             @RequestParam("immagine") MultipartFile file, Model model) {
	    if (!ingredienteRepository.existsByName(ingrediente.getName())) {
	        if (!file.isEmpty()) {
	            try {
	                // Salva il file sul server
	                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	                Path path = Paths.get(UPLOAD_DIR + File.separator + fileName);
	                Files.write(path, file.getBytes());
	                ingrediente.setPhoto(fileName);

	                // Salva l'ingrediente
	                this.ingredienteService.save(ingrediente);
	                model.addAttribute("ingrediente", ingrediente);
	                return "ingrediente.html";
	            } catch (IOException e) {
	                e.printStackTrace();
	                model.addAttribute("messaggioErrore", "Errore caricamento dell'immagine");
	                return "/admin/formNewIngrediente.html";
	            }
	        } else {
	            model.addAttribute("messaggioErrore", "immagine vuota...");
	            return "/admin/formNewIngrediente.html";
	        }
	    } else {
	        model.addAttribute("messaggioErrore", "Questo ingrediente esiste già");
	        return "/admin/formNewIngrediente.html";
	    }
	}
	
	@GetMapping("/admin/manageIngredienti")
	public String ShowIngredientiAdmin(Model model) {
		model.addAttribute("ingredienti", this.ingredienteService.findAll());
		return "/admin/manageIngredienti.html";
	}
	
	@GetMapping(value = "/admin/deleteIngrediente/{ingredienteId}")
	public String deleteIngredienteAdmin(@PathVariable("ingredienteId") Long ingredienteId, Model model) {
		ingredienteService.deleteById(ingredienteId);
        return "redirect:/admin/manageIngredienti";
	}
}
