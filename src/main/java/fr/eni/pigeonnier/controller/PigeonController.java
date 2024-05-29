package fr.eni.pigeonnier.controller;

import fr.eni.pigeonnier.entity.Pigeon;
import fr.eni.pigeonnier.entity.Target;
import fr.eni.pigeonnier.service.PigeonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;


// http://localhost:8080
@Controller
@RequestMapping
public class PigeonController {
    @Autowired
    PigeonService pigeonService;

    @GetMapping("/")
    public String showAccueil() {
        return "accueil";
    }

    @GetMapping("/pigeon")
    public String showList(Model model) {
        model.addAttribute("lstPigeons", pigeonService.getAllPigeons());
        return "pigeon";
    }

    @GetMapping("/pigeon/add")
    public String add(@ModelAttribute("pigeon") Pigeon pigeon) {
        return "add";
    }


    @PostMapping("/pigeon/add")
    public String valid(@Valid @ModelAttribute("pigeon") Pigeon pigeon, BindingResult errors) {
        if(errors.hasErrors()) {
            return "pigeon";
        }
        pigeonService.addPigeon(pigeon);
        // Redirection pour éviter la soumission multiple
        return "redirect:/pigeon";
    }

    @GetMapping("/pigeon/catch")
    public String capture(@ModelAttribute("target") Target target) {
        return "catch";
    }

    @PostMapping("/pigeon/catch")
    public String capture(@Valid @ModelAttribute("target") Target target, BindingResult errors, RestTemplate restTemplate) {
        if(errors.hasErrors()) {
            return "catch";
        }

        Pigeon[] re = restTemplate.getForObject("http://10.46.101."+target.getIp()+":8080/rapt/"+target.getCode(), Pigeon[].class);
        if (re != null) {
            for (Pigeon pigeon : re) {
                System.out.println("capture de "+pigeon.getNom());
                pigeon.setProprio(pigeon.getProprio());
                pigeon.setIdPigeon(null);
                pigeonService.addPigeon(pigeon);
            }
        }
        return "redirect:/pigeon";
    }

}
