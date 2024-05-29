package fr.eni.pigeonnier.repository;

import org.springframework.data.repository.CrudRepository;

import fr.eni.pigeonnier.entity.Utilisateur;


public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer>{

    Utilisateur findByPseudo(String pseudo);

}
