package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cuoco {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String cognome;
	private java.time.LocalDate nascita;
	private String immagine;
	
	/*ASSOCIAZIONI*/
	@OneToMany(mappedBy = "creatore")
	private List<Ricetta> ricetteCreate;
	
	/*GETTER E SETTER*/
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
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public java.time.LocalDate getData_nascita() {
		return nascita;
	}
	public void setData_nascita(java.time.LocalDate data_nascita) {
		this.nascita = data_nascita;
	}
	public String getImmagine() {
		return immagine;
	}
	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cognome, nascita, nome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuoco other = (Cuoco) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(nascita, other.nascita)
				&& Objects.equals(nome, other.nome);
	}
}
