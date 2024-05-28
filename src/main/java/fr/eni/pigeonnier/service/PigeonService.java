package fr.eni.pigeonnier.service;

import fr.eni.pigeonnier.entity.Pigeon;
import fr.eni.pigeonnier.exceptions.PigeonException;

import java.util.List;

public interface PigeonService {
    void addPigeon(Pigeon pigeon);
    List<Pigeon> getAllPigeons();
    void delPigeon(Pigeon pigeon);
    List<Pigeon> getByCode(String numero);
    List<Pigeon> rapt(String code);

}
