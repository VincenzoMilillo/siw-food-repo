package it.uniroma3.siw.model;

import java.util.Objects;
import java.util.List;

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
	private String name;
	private String description;
	private String photo;
	
	/*ASSOCIAZIONI*/
	@ManyToOne
	private Cuoco creatore;
	@ManyToMany(mappedBy="ricetteContenenti")
	private List<Ingrediente> ingredientiContenuti;
	
	/*GETTER E SETTER*/
	public List<Ingrediente> getIngredienti() {
		return ingredientiContenuti;
	}
	public void setIngredienti(List<Ingrediente> ingredienti) {
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
		return name;
	}
	public void setNome(String nome) {
		this.name = nome;
	}
	public String getDescrizione() {
		return description;
	}
	public void setDescrizione(String descrizione) {
		this.description = descrizione;
	}
	public String getImmagine() {
		return photo;
	}
	public void setImmagine(String immagine) {
		this.photo = immagine;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
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
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
}
