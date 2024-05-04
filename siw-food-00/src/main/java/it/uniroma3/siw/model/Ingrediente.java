package it.uniroma3.siw.model;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Ingrediente {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String stagionalita;
	private String immagine;
	private String Descrizione;
	
	/*ASSOCIAZIONI*/
	@ManyToMany
	private Set<Ricetta> ricetteContenenti;
	
	/*GETTER E SETTER*/
	public Set<Ricetta> getRicette() {
		return ricetteContenenti;
	}
	public void setRicette(Set<Ricetta> ricette) {
		this.ricetteContenenti = ricette;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getStagionalita() {
		return stagionalita;
	}
	public void setStagionalita(String stagionalita) {
		this.stagionalita = stagionalita;
	}
	
	public String getImmagine() {
		return immagine;
	}
	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}
	public String getDescrizione() {
		return Descrizione;
	}
	public void setDescrizione(String descrizione) {
		Descrizione = descrizione;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, nome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingrediente other = (Ingrediente) obj;
		return Objects.equals(id, other.id) && Objects.equals(nome, other.nome);
	}
}
