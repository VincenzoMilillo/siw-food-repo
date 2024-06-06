package it.uniroma3.siw.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.repository.CuocoRepository;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.RicettaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class CuocoController {
	
	@Autowired CuocoService cuocoService;
	@Autowired CuocoRepository cuocoRepository;
	@Autowired RicettaService ricettaService;
	private static String UPLOAD_DIR = "C:\\Users\\utente\\Desktop\\siw-food-ws\\siw-food-00\\src\\main\\resources\\static\\images";
	
	/*PARTE DI CONTOLLER RELATIVA ALL'UTENTE CASUALE*/
	
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

	/*PARTE DI CONTOLLER RELATIVA ALL'ADMIN*/
	
	@GetMapping(value = "/admin/formNewCuoco")
	public String formNewCuoco(Model model) {
		model.addAttribute("cuoco", new Cuoco());
		return "admin/formNewCuoco.html";
	}
	
	@GetMapping("/admin/manageCuochi")
	public String ShowCuocoAdmin(Model model) {
		model.addAttribute("cuochi", this.cuocoService.findAll());
		return "/admin/manageCuochi.html";
	}
	
	@PostMapping("/admin/cuochi")
	public String newCuoco(@ModelAttribute("cuoco") Cuoco cuoco, @RequestParam("immagine") MultipartFile file, Model model) {
	    if (!cuocoRepository.existsByNameAndSurname(cuoco.getName(), cuoco.getSurname())) {
	        if (!file.isEmpty()) {
	            try {
	                // Salva il file sul server
	                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	                Path path = Paths.get(UPLOAD_DIR + File.separator + fileName);
	                Files.write(path, file.getBytes());
	                cuoco.setPhoto(fileName);
	                
	                // Salva il cuoco
	                this.cuocoService.save(cuoco);
	                
	                model.addAttribute("cuoco", cuoco);
	                return "cuoco";
	            } catch (IOException e) {
	                e.printStackTrace();
	                model.addAttribute("messaggioErrore", "Errore nel caricamento dell'immagine...");
	                return "formNewCuoco.html";
	            }
	        } else {
	            model.addAttribute("messaggioErrore", "immagine vuota...");
	            return "formNewCuoco.html";
	        }
	    } else {
	        model.addAttribute("messaggioErrore", "Questo cuoco è già presente...");
	        return "formNewCuoco.html";
	    }
	}
}
