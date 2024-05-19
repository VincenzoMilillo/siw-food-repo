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
	private String photo;
	private String description;
	
	/*ASSOCIAZIONI*/
	@ManyToOne
	private Cuoco cuoco;
	@ManyToMany(mappedBy="ricette")
	private List<Ingrediente> ingredientiContenuti;
	
	/*GETTER E SETTER*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Cuoco getCuoco() {
		return cuoco;
	}
	public void setCuoco(Cuoco cuoco) {
		this.cuoco = cuoco;
	}
	public List<Ingrediente> getIngredientiContenuti() {
		return ingredientiContenuti;
	}
	public void setIngredientiContenuti(List<Ingrediente> ingredientiContenuti) {
		this.ingredientiContenuti = ingredientiContenuti;
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
