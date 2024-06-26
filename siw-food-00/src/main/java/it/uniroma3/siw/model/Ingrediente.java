package it.uniroma3.siw.model;

import java.util.Map;
import java.util.Objects;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import org.springframework.web.multipart.MultipartFile;

@Entity
public class Ingrediente {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String season;
	public String photo;
	private String quantity;
	@Column(length = 2000)
	private String description;
	private String unitaDiMisura;
	@Transient
	private MultipartFile immagine;
    
	
	@ElementCollection
	/*Questa annotazione viene utilizzata per indicare a JPA che un campo rappresenta una collezione di elementi
	 *  che devono essere considerati come parte integrante (embedded) dell'entità padre, anziché come entità distinte.
	 *  Questo è utile quando hai una collezione di valori associati a un'entità principale, ma non necessariamente vuoi
	 *  trattare quei valori come entità a sé stanti. Ad esempio, se hai una classe Ingrediente e desideri memorizzare 
	 *  una collezione di quantità di ingredienti per una ricetta specifica, puoi utilizzare @ElementCollection per indicare
	 *  che la mappa di quantità non è un'entità separata ma fa parte dell'entità Ingrediente*/
	
	 @CollectionTable(name = "quantita_ricetta", joinColumns = @JoinColumn(name = "ingrediente_id"))
	/*Questa annotazione viene utilizzata per personalizzare la tabella utilizzata per memorizzare la collezione di elementi
	 *  definita con @ElementCollection. Quando si utilizza @ElementCollection, JPA crea una tabella separata per memorizzare
	 *  gli elementi della collezione. L'annotazione @CollectionTable consente di specificare il nome della tabella e le colonne 
	 *  di join necessarie per associare la tabella della collezione con l'entità principale. Ad esempio, nel caso dell'entità Ingrediente,
	 *  @CollectionTable viene utilizzato per specificare il nome della tabella (quantita_ricetta) e la colonna (ingrediente_id)
	 *  che verrà utilizzata per collegare la tabella delle quantità alla tabella degli ingredienti.*/

	@Column(name = "quantita")
	private Map<Long, Integer> quantitaToRicetta;


	public Map<Long, Integer> getQuantitaToRicetta() {
		return quantitaToRicetta;
	}

	public void setQuantitaToRicetta(Map<Long, Integer> quantitaToRicetta) {
		this.quantitaToRicetta = quantitaToRicetta;
	}

	public String getUnitaDiMisura() {
		return unitaDiMisura;
	}

	public void setUnitaDiMisura(String unitaDiMisura) {
		this.unitaDiMisura = unitaDiMisura;
	}
	/*GETTER E SETTER*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
}
