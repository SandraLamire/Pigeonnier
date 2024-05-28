package fr.eni.pigeonnier.service;

import fr.eni.pigeonnier.entity.Pigeon;
import fr.eni.pigeonnier.exceptions.PigeonException;
import fr.eni.pigeonnier.repository.PigeonRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PigeonServiceImpl implements PigeonService {
    private final String PROPRIO = "SANDRA L.";
    @Autowired
    PigeonRepository repo;

    @PostConstruct
    public void init() {
        addPigeon(new Pigeon("Paulot"));
        addPigeon(new Pigeon("Pistache"));
        addPigeon(new Pigeon("PiouPiou"));

        System.out.println();
        getAllPigeons().forEach(System.out::println);
        System.out.println();
        System.out.println(getAllPigeons().stream().findFirst().orElse(null));
    }


    @Override
    public void addPigeon(Pigeon pigeon) {
        if (pigeon.getProprio() == null) {
            pigeon.setProprio(PROPRIO);
        }
        Integer rs = (int) (Math.random() * 10);
        pigeon.setCode(rs.toString());
        System.out.println("AJOUT DE "+pigeon);
        repo.save(pigeon);
    }

    @Override
    public List<Pigeon> getAllPigeons() {
        return (List<Pigeon>) repo.findAll();
    }

    @Override
    public void delPigeon(Pigeon pigeon) {
        repo.delete(pigeon);
    }

    @Override
    public List<Pigeon> getByCode(String numero) {
        return repo.findByCode(numero);
    }


    @Override
    public List<Pigeon> rapt(String code) {
        System.out.println("VOUS AVEZ ETE ATTAQUE");
        List<Pigeon> lst = getByCode(code);
        if(lst.size()>0) {
            lst.forEach(p->{
                System.out.println(p.getNom()+ " est capturé !");
                delPigeon(p);
            });
        }
        else {
            System.out.println("le pigeonnier a resisté !!");
        }
        return lst;
    }

}