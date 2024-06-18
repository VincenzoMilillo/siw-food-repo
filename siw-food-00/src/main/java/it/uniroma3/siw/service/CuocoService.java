package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.repository.CuocoRepository;

@Service
public class CuocoService {
	@Autowired CuocoRepository cuocoRepository;

	public Cuoco findById(Long id) {
		return cuocoRepository.findById(id).get();
	}

	public Iterable<Cuoco> findAll() {
		return cuocoRepository.findAll();
	}

	public Cuoco save(Cuoco artist) {
		return cuocoRepository.save(artist);
	}
	
	public Object findByName(String nome) {
		return cuocoRepository.findByName(nome);
	}
	
	public Object findByNameAndSurname(String nome, String cognome) {
		return cuocoRepository.findByNameAndSurname(nome, cognome);
	}

	public void deleteById(Long id) {
		cuocoRepository.deleteById(id);
	}

}
