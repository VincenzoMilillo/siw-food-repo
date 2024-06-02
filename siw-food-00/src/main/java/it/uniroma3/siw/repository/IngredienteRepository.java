package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {
	
	public List<Ingrediente> findByName(String nome);
	boolean existsByName(String name);
	
	/*query al database per trovare gli ingredienti che non sono già nella ricetta*/
	@Query(value="select * "
	        + "from ingrediente i "
	        + "where i.id not in "
	        + "(select ir.ingredienti_contenuti_id "
	        + "from ingrediente_ricette ir "
	        + "where ir.ricette_id = :ricettaId)", nativeQuery=true)
	public Iterable<Ingrediente> findIngredientiNotInRicetta(@Param("ricettaId") Long ricettaId);

}
