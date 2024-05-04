package it.uniroma3.siw.model;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Ricetta {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String descrizione;
	private String immagine;
	
	/*ASSOCIAZIONI*/
	@ManyToOne
	private Cuoco creatore;
	@ManyToMany(mappedBy="ricetteContenenti")
	private Set<Ingrediente> ingredientiContenuti;
	
	/*GETTER E SETTER*/
	public Set<Ingrediente> getIngredienti() {
		return ingredientiContenuti;
	}
	public void setIngredienti(Set<Ingrediente> ingredienti) {
		this.ingredientiContenuti = ingredienti;
	}
	public Cuoco getCreatore() {
		return creatore;
	}
	public void setCreatore(Cuoco creatore) {
		this.creatore = creatore;
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
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getImmagine() {
		return immagine;
	}
	public void setImmagine(String immagine) {
		this.immagine = immagine;
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
		Ricetta other = (Ricetta) obj;
		return Objects.equals(id, other.id) && Objects.equals(nome, other.nome);
	}
}
