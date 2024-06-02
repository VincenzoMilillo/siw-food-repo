package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.repository.IngredienteRepository;
import it.uniroma3.siw.repository.RicettaRepository;

@Service
public class IngredienteService {
	
	@Autowired IngredienteRepository ingredienteRepository;
	@Autowired RicettaRepository ricettaRepository;

	public Ingrediente findById(Long id) {
		return ingredienteRepository.findById(id).get();
	}

	public Iterable<Ingrediente> findAll() {
		return ingredienteRepository.findAll();
	}
	
	public Iterable<Ingrediente> findByName(String name) {
		return ingredienteRepository.findByName(name);
	}
	
	public Ingrediente save(Ingrediente ingrediente) {
		return ingredienteRepository.save(ingrediente);
	}
	
}
