package fr.eni.pigeonnier.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import fr.eni.pigeonnier.entity.Utilisateur;
import fr.eni.pigeonnier.repository.UtilisateurRepository;


@Service
public class UtilisateurService {
    @Autowired
    UtilisateurRepository repo;

    @Autowired
    PasswordEncoder encodeur;


    @Transactional
    public void addUtilisateur(Utilisateur utilisateur) {
        // encodage du mot de passe
        utilisateur.setMdp(encodeur.encode(utilisateur.getMdp()));

        // insertion en base
        repo.save(utilisateur);
    }

    public List<Utilisateur> getAllUtilisateurs() {
        return (List<Utilisateur>) repo.findAll();
    }

}
