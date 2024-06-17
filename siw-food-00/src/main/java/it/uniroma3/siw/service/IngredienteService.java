package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Ricetta;
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

	public void deleteById(Long id) {
		ingredienteRepository.deleteById(id);
	}
	
	@Transactional
    public void deleteIngrediente(Long id) {
        Optional<Ingrediente> optionalIngrediente = ingredienteRepository.findById(id);

        if (optionalIngrediente.isPresent()) {
            Ingrediente ingrediente = optionalIngrediente.get();
            
            // Rimuovere l'associazione con le ricette
            for (Ricetta ricetta : ricettaRepository.findAllByIngredientiContenuti(ingrediente)) {
                ricetta.getIngredientiContenuti().remove(ingrediente);
                ricettaRepository.save(ricetta);
            }

            // Eliminare l'ingrediente
            ingredienteRepository.delete(ingrediente);
        }
    }
}
