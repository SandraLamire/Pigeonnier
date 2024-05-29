package fr.eni.pigeonnier.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Pigeon {
	@Id
	@GeneratedValue
	private Integer idPigeon;
	@NotBlank
	private String nom;
	@NotBlank
	private String proprio = "Sandra L.";
	private String code;


	public Pigeon(String nom) {
		super();
		this.nom = nom;
	}
}
