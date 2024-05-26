package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cuoco;

public interface CuocoRepository extends CrudRepository<Cuoco, Long> {
	
	public List<Cuoco> findByName(String name);
	
	public Optional<Cuoco> findById(Long Id);
	
	public Cuoco findByNameAndSurname(String name, String surname);
	
	public boolean existsByNameAndSurname(String name, String surname);
}
