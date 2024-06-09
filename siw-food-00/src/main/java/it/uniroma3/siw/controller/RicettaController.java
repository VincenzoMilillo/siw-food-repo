package it.uniroma3.siw.controller;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import it.uniroma3.siw.controller.validator.RicettaValidator;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ingrediente;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	private static String UPLOAD_DIR = "C:\\Users\\utente\\Desktop\\siw-food-ws\\siw-food-00\\src\\main\\resources\\static\\images";
	
	/*PARTE DI CONTOLLER RELATIVA ALL'UTENTE CASUALE*/
	
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
	
	/*PARTE DI CONTOLLER RELATIVA AL CUOCO*/
	
	@GetMapping(value = "/cuoco/formNewRicetta/{username}")
	public String formNewRicettaCuoco(@PathVariable("username") String username, Model model) {
		Credentials tempUser = credentialsRepository.findByUsername(username);
		User currentUser = tempUser.getUser();
		Cuoco currentCuoco = this.cuocoRepository.findByNameAndSurname(currentUser.getName(), currentUser.getSurname());
		Ricetta ricetta = new Ricetta();
		model.addAttribute("cuoco", currentCuoco);
		model.addAttribute("cuocoId", currentCuoco.getId());
		model.addAttribute("ricetta", ricetta);
		model.addAttribute("userDetails", tempUser);
		return "cuoco/formNewRicetta.html";
	}
	
	@PostMapping("/cuoco/ricetta")
	public String newRicettaCuoco(@Valid @ModelAttribute("ricetta") Ricetta ricetta, BindingResult bindingResult, @RequestParam("username") String username,
			@RequestParam("immagine") MultipartFile file, Model model) {
		Credentials tempUser = credentialsRepository.findByUsername(username);
		User currentUser = tempUser.getUser();
		Cuoco currentCuoco = this.cuocoRepository.findByNameAndSurname(currentUser.getName(), currentUser.getSurname());
		ricetta.setCuoco(currentCuoco);

		this.ricettaValidator.validate(ricetta, bindingResult);
		if (!bindingResult.hasErrors()) {
			if (!file.isEmpty()) {
				try {
					// Salva il file sul server
					String fileName = StringUtils.cleanPath(file.getOriginalFilename());
					Path path = Paths.get(UPLOAD_DIR + File.separator + fileName);
					Files.write(path, file.getBytes());
					ricetta.setPhoto(fileName);

					// Salva la ricetta
					this.ricettaRepository.save(ricetta);

					model.addAttribute("ricetta", ricetta);
					return "ricetta";
				} catch (IOException e) {
					e.printStackTrace();
					model.addAttribute("messaggioErrore", "Errore nel caricamento dell'immagine...");
					return "formNewRicetta.html";
				}
			} else {
				model.addAttribute("messaggioErrore", "immagine vuota...");
				return "formNewRicetta.html";
			}
		} else {
			return "cuoco/formNewRicetta.html";
		}
	}
	
	@GetMapping("/cuoco/manageRicette")
	public String ShowRicettaCuoco(Model model) {
		model.addAttribute("ricette", this.ricettaService.findAll());
		return "/cuoco/manageRicette.html";
	}
	
	@GetMapping(value = "/cuoco/formUpdateRicetta/{id}/{username}")
	public String formUpdateRicettaCuoco(@PathVariable("id") Long id, @PathVariable("username") String username, Model model, RedirectAttributes redirectAttributes) {
		Credentials tempUser = credentialsRepository.findByUsername(username); //trova l'utente dal DB
		User currentUser = tempUser.getUser();	//prendi l'user corrente
		Ricetta ricetta = ricettaRepository.findById(id).orElse(null);	//prendi la ricetta dal DB
		// se il cuoco corrente non è il creatore della ricetta...
		if (ricetta == null || ricetta.getCuoco() == null || !ricetta.getCuoco().getName().equals(currentUser.getName())
				|| !ricetta.getCuoco().getSurname().equals(currentUser.getSurname())) {
			// Gestisci il caso di accesso non autorizzato
			redirectAttributes.addFlashAttribute("messaggioErrore", "Non puoi modificare ricette che non ti appartengono...");
			return "redirect:/cuoco/manageRicette";
		}

		// Aggiungi la ricetta al modello e restituisci la vista
		model.addAttribute("ricetta", ricetta);
		return "cuoco/formUpdateRicetta.html";
	}
	
	private List<Ingrediente> ingredientiToAdd(Long ricettaId) {
		List<Ingrediente> ingredientiToAdd = new ArrayList<>();

		for (Ingrediente i : ingredienteRepository.findIngredientiNotInRicetta(ricettaId)) {
			ingredientiToAdd.add(i);
		}
		return ingredientiToAdd;
	}
	
	@GetMapping("/cuoco/updateIngredienti/{id}")
	public String updateIngredientiCuoco(@PathVariable("id") Long id, Model model) {

		List<Ingrediente> ingredientiToAdd = this.ingredientiToAdd(id);
		model.addAttribute("ingredientiToAdd", ingredientiToAdd);
		model.addAttribute("ricetta", this.ricettaRepository.findById(id).get());

		return "cuoco/addIngredienteToRicetta.html";
	}
	
	@GetMapping(value = "/cuoco/addIngredienteToRicetta/{ingredienteId}/{ricettaId}")
	public String addIngredienteToRicettaCuoco(@PathVariable("ingredienteId") Long ingredienteId,
			@PathVariable("ricettaId") Long ricettaId, Model model) {
		Ricetta ricetta = this.ricettaRepository.findById(ricettaId).get();
		Ingrediente ingrediente = this.ingredienteRepository.findById(ingredienteId).get();
		Set<Ingrediente> ingredienti = ricetta.getIngredientiContenuti();
		ingredienti.add(ingrediente);
		this.ricettaRepository.save(ricetta);

		List<Ingrediente> ingredientiToAdd = ingredientiToAdd(ricettaId);

		model.addAttribute("ricetta", ricetta);
		model.addAttribute("ingredientiToAdd", ingredientiToAdd);

		return "cuoco/addIngredienteToRicetta.html";
	}
	

	@GetMapping(value = "/cuoco/removeIngredienteFromRicetta/{ingredienteId}/{ricettaId}")
	public String removeIngredienteFromRicettaCuoco(@PathVariable("ingredienteId") Long ingredienteId,
			@PathVariable("ricettaId") Long ricettaId, Model model) {
		Ricetta ricetta = this.ricettaRepository.findById(ricettaId).get();
		Ingrediente ingrediente = this.ingredienteRepository.findById(ingredienteId).get();
		Set<Ingrediente> ingredientiUtilizzati = ricetta.getIngredientiContenuti();
		ingredientiUtilizzati.remove(ingrediente);
		this.ricettaRepository.save(ricetta);

		List<Ingrediente> ingredientiToAdd = ingredientiToAdd(ricettaId);

		model.addAttribute("ricetta", ricetta);
		model.addAttribute("ingredientiToAdd", ingredientiToAdd);

		return "cuoco/addIngredienteToRicetta.html";
	}
	
	@GetMapping(value = "/cuoco/updateQuantita/{ingredienteId}/{ricettaId}")
	public String formUpdateQuantita(@PathVariable("ricettaId") Long ricettaId,
			@PathVariable("ingredienteId") Long ingredienteId, Model model) {
		model.addAttribute("ricetta", ricettaRepository.findById(ricettaId).get());
		model.addAttribute("ingrediente", ingredienteRepository.findById(ingredienteId).get());
		return "/cuoco/updateQuantita.html";
	}

	@PostMapping(value = "/cuoco/updateQuantita")
	public String updateQuantita(@RequestParam("ingredienteId") Long ingredienteId,
			@RequestParam("ricettaId") Long ricettaId, @RequestParam("quantitaValore") Integer quantitaValore,
			@RequestParam("quantitaUnita") String quantitaUnita, Model model) {
		Optional<Ingrediente> ingredienteOpt = ingredienteRepository.findById(ingredienteId);
		Optional<Ricetta> ricettaOpt = ricettaRepository.findById(ricettaId);

		if (ingredienteOpt.isPresent() && ricettaOpt.isPresent()) {
			Ingrediente ingrediente = ingredienteOpt.get();
			Ricetta ricetta = ricettaOpt.get();
			Map<Long, Integer> quantitaToRicetta = ingrediente.getQuantitaToRicetta();
			quantitaToRicetta.put(ricetta.getId(), quantitaValore);
			ingrediente.setUnitaDiMisura(quantitaUnita);
			ingrediente.setQuantitaToRicetta(quantitaToRicetta);
			ingredienteRepository.save(ingrediente); // Aggiorna l'ingrediente nel database
			model.addAttribute("ricetta", ricettaRepository.findById(ricettaId).get());
			model.addAttribute("ingrediente", ingredienteRepository.findById(ingredienteId).get());
			model.addAttribute("quantitaValore", quantitaValore);
		}
		return "cuoco/formUpdateRicetta.html";
	}
	
	@GetMapping(value = "/cuoco/deleteRicetta/{ricettaId}")
	public String deleteRicettaCuoco(@PathVariable("ricettaId") Long ricettaId, Model model) {
		Ricetta ricetta = ricettaService.findById(ricettaId);
		ricetta.setIngredientiContenuti(null);
		ricettaService.deleteById(ricetta.getId());
		return "redirect:/cuoco/manageRicette.html";
	}
	
	/*PARTE DI CONTOLLER RELATIVA ALL'ADMIN*/

	@GetMapping(value = "/admin/formNewRicetta")
	public String formNewRicetta(Model model) {
		model.addAttribute("ricetta", new Ricetta());
		return "/admin/formNewRicetta.html";
	}
	
	@PostMapping("/admin/ricetta")
	public String newRicettaAdmin(@Valid @ModelAttribute("ricetta") Ricetta ricetta, BindingResult bindingResult, @RequestParam("username") String username,
			@RequestParam("immagine") MultipartFile file, Model model) {
		Credentials tempUser = credentialsRepository.findByUsername(username);
		User currentUser = tempUser.getUser();
		Cuoco currentCuoco = this.cuocoRepository.findByNameAndSurname(currentUser.getName(), currentUser.getSurname());
		ricetta.setCuoco(currentCuoco);

		this.ricettaValidator.validate(ricetta, bindingResult);
		if (!bindingResult.hasErrors()) {
			if (!file.isEmpty()) {
				try {
					// Salva il file sul server
					String fileName = StringUtils.cleanPath(file.getOriginalFilename());
					Path path = Paths.get(UPLOAD_DIR + File.separator + fileName);
					Files.write(path, file.getBytes());
					ricetta.setPhoto(fileName);

					// Salva la ricetta
					this.ricettaRepository.save(ricetta);

					model.addAttribute("ricetta", ricetta);
					return "ricetta";
				} catch (IOException e) {
					e.printStackTrace();
					model.addAttribute("messaggioErrore", "Errore nel caricamento dell'immagine...");
					return "admin/formNewRicetta.html";
				}
			} else {
				model.addAttribute("messaggioErrore", "immagine vuota...");
				return "admin/formNewRicetta.html";
			}
		} else {
			return "admin/formNewRicetta.html";
		}
	}
	
	@GetMapping("/admin/manageRicette")
	public String ShowRicettaAdmin(Model model) {
		model.addAttribute("ricette", this.ricettaService.findAll());
		return "/admin/manageRicette.html";
	}
	
	@GetMapping(value = "/admin/formUpdateRicetta/{id}")
	public String formUpdateRicetta(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ricetta", ricettaRepository.findById(id).get());
		return "admin/formUpdateRicetta.html";
	}
	
	@GetMapping("/admin/updateIngredienti/{id}")
	public String updateIngredienti(@PathVariable("id") Long id, Model model) {

		List<Ingrediente> ingredientiToAdd = this.ingredientiToAdd(id);
		model.addAttribute("ingredientiToAdd", ingredientiToAdd);
		model.addAttribute("ricetta", this.ricettaRepository.findById(id).get());

		return "admin/addIngredienteToRicetta.html";
	}
	
	@GetMapping(value = "/admin/addIngredienteToRicetta/{ingredienteId}/{ricettaId}")
	public String addIngredienteToRicettaAdmin(@PathVariable("ingredienteId") Long ingredienteId,
			@PathVariable("ricettaId") Long ricettaId, Model model) {
		Ricetta ricetta = this.ricettaRepository.findById(ricettaId).get();
		Ingrediente ingrediente = this.ingredienteRepository.findById(ingredienteId).get();
		Set<Ingrediente> ingredienti = ricetta.getIngredientiContenuti();
		ingredienti.add(ingrediente);
		this.ricettaRepository.save(ricetta);

		List<Ingrediente> ingredientiToAdd = ingredientiToAdd(ricettaId);

		model.addAttribute("ricetta", ricetta);
		model.addAttribute("ingredientiToAdd", ingredientiToAdd);

		return "admin/addIngredienteToRicetta.html";
	}
	

	@GetMapping(value = "/admin/removeIngredienteFromRicetta/{ingredienteId}/{ricettaId}")
	public String removeIngredienteFromRicettaAdmin(@PathVariable("ingredienteId") Long ingredienteId,
			@PathVariable("ricettaId") Long ricettaId, Model model) {
		Ricetta ricetta = this.ricettaRepository.findById(ricettaId).get();
		Ingrediente ingrediente = this.ingredienteRepository.findById(ingredienteId).get();
		Set<Ingrediente> ingredientiUtilizzati = ricetta.getIngredientiContenuti();
		ingredientiUtilizzati.remove(ingrediente);
		this.ricettaRepository.save(ricetta);

		List<Ingrediente> ingredientiToAdd = ingredientiToAdd(ricettaId);

		model.addAttribute("ricetta", ricetta);
		model.addAttribute("ingredientiToAdd", ingredientiToAdd);

		return "admin/addIngredienteToRicetta.html";
	}
	
	@GetMapping(value = "/admin/updateQuantita/{ingredienteId}/{ricettaId}")
	public String formUpdateQuantitaAdmin(@PathVariable("ricettaId") Long ricettaId,
			@PathVariable("ingredienteId") Long ingredienteId, Model model) {
		model.addAttribute("ricetta", ricettaRepository.findById(ricettaId).get());
		model.addAttribute("ingrediente", ingredienteRepository.findById(ingredienteId).get());
		return "/admin/updateQuantita.html";
	}

	@PostMapping(value = "/admin/updateQuantita")
	public String updateQuantitaAdmin(@RequestParam("ingredienteId") Long ingredienteId,
			@RequestParam("ricettaId") Long ricettaId, @RequestParam("quantitaValore") Integer quantitaValore,
			@RequestParam("quantitaUnita") String quantitaUnita, Model model) {
		Optional<Ingrediente> ingredienteOpt = ingredienteRepository.findById(ingredienteId);
		Optional<Ricetta> ricettaOpt = ricettaRepository.findById(ricettaId);

		if (ingredienteOpt.isPresent() && ricettaOpt.isPresent()) {
			Ingrediente ingrediente = ingredienteOpt.get();
			Ricetta ricetta = ricettaOpt.get();
			Map<Long, Integer> quantitaToRicetta = ingrediente.getQuantitaToRicetta();
			quantitaToRicetta.put(ricetta.getId(), quantitaValore);
			ingrediente.setUnitaDiMisura(quantitaUnita);
			ingrediente.setQuantitaToRicetta(quantitaToRicetta);
			ingredienteRepository.save(ingrediente); // Aggiorna l'ingrediente nel database
			model.addAttribute("ricetta", ricettaRepository.findById(ricettaId).get());
			model.addAttribute("ingrediente", ingredienteRepository.findById(ingredienteId).get());
			model.addAttribute("quantitaValore", quantitaValore);
		}
		return "admin/formUpdateRicetta.html";
	}
	
	@GetMapping(value = "/admin/deleteRicetta/{ricettaId}")
	public String deleteRicettaAdmin(@PathVariable("ricettaId") Long ricettaId, Model model) {
		Ricetta ricetta = ricettaService.findById(ricettaId);
		ricetta.setIngredientiContenuti(null);
		ricettaService.deleteById(ricetta.getId());
		return "redirect:/admin/manageRicette";
	}
	
	@GetMapping(value = "/admin/setCuocoToRicetta/{cuocoId}/{ricettaId}")
	public String setDirectorToMovie(@PathVariable("cuocoId") Long cuocoId, @PathVariable("ricettaId") Long ricettaId,
			Model model) {

		Cuoco cuoco = this.cuocoService.findById(cuocoId);
		Ricetta ricetta = this.ricettaRepository.findById(ricettaId).get();
		ricetta.setCuoco(cuoco);
		this.ricettaRepository.save(ricetta);

		model.addAttribute("ricetta", ricetta);
		return "admin/formUpdateRicetta.html";
	}
	
	@GetMapping(value = "/admin/addCuoco/{idRicetta}")
	public String addArtist(@PathVariable("idRicetta") Long ricettaId, Model model) {
		model.addAttribute("cuochi", cuocoService.findAll());
		model.addAttribute("ricetta", ricettaRepository.findById(ricettaId).get());
		return "/admin/addCuoco.html";
	}
}
