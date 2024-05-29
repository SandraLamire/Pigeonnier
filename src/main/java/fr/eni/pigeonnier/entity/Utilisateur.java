package fr.eni.pigeonnier.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue
    private Integer idUtilisateur;
    private String pseudo;
    private String mdp;
    private String nom;
    private String prenom;
    private String roles;

    public Utilisateur(String pseudo, String mdp, String nom, String prenom, String roles) {
        super();
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.nom = nom;
        this.prenom = prenom;
        this.roles = roles;
    }




}