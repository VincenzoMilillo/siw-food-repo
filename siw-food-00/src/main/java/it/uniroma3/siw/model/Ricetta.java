package it.uniroma3.siw.model;

import java.util.Objects;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;


@Entity
public class Ricetta {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String photo;
	@Column(length = 2000)
	private String description;
	
	/*ASSOCIAZIONI*/
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private Cuoco cuoco;
	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private Set<Ingrediente> ingredientiContenuti;
	
	@Transient
	private MultipartFile immagine;
	
	/*GETTER E SETTER*/
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public Set<Ingrediente> getIngredientiContenuti() {
		return ingredientiContenuti;
	}
	public void setIngredientiContenuti(Set<Ingrediente> ingredientiContenuti) {
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
