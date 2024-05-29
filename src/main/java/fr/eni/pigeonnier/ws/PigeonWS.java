package fr.eni.pigeonnier.ws;

import fr.eni.pigeonnier.entity.Pigeon;
import fr.eni.pigeonnier.exceptions.PigeonException;
import fr.eni.pigeonnier.service.PigeonService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/pigeons")
public class PigeonWS {
    @Autowired
    private PigeonService service;


    @GetMapping("/rapt/{numero}")
    public ResponseEntity<List<Pigeon>> rapt(@PathVariable("numero") String numero) {
        List<Pigeon> lst = service.rapt(numero);
        if(lst.size()>0) {
            return ResponseEntity.ok().body(lst);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/vachercher/{ip}/{code}")
    public ResponseEntity<String> vachercher(@PathVariable("ip") String ip, @PathVariable("code")String code, RestTemplate restTemplate){
        Pigeon[] re = restTemplate.getForObject("http://10.46.101."+ip+":8080/pigeon/rapt/"+code, Pigeon[].class);
        for (Pigeon pigeon : re) {
            System.out.println("capture de "+pigeon.getNom());
            pigeon.setIdPigeon(null);
            service.addPigeon(pigeon);
        }
        return ResponseEntity.ok().body(re.length+" pigeon(s) volé");
    }
}

