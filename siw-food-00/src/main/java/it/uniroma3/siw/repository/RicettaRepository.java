package it.uniroma3.siw.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Ricetta;

public interface RicettaRepository extends CrudRepository<Ricetta, Long> {
	public boolean existsByName(String nome);
	public List<Ricetta> findByName(String nome);
	public List<Ricetta> findByCuoco(Cuoco cuoco);
	public boolean existsByNameAndCuoco(String nome, Cuoco cuoco);
	public Ricetta[] findAllByIngredientiContenuti(Ingrediente ingrediente);
}
