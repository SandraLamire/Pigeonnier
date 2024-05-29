package fr.eni.pigeonnier;


import fr.eni.pigeonnier.entity.Utilisateur;
import fr.eni.pigeonnier.service.UtilisateurService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PigeonApplication {
	@Autowired
	public UtilisateurService service;


	@PostConstruct
	public void init() {
		service.addUtilisateur(new Utilisateur("s","s","L","Sandra","ADMIN,USER"));
		service.getAllUtilisateurs().forEach(System.out::println);
	}

	public static void main(String[] args) {
		SpringApplication.run(PigeonApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
