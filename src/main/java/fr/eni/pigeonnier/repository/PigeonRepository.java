package fr.eni.pigeonnier.repository;

import fr.eni.pigeonnier.entity.Pigeon;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PigeonRepository extends CrudRepository<Pigeon, Integer> {
    List<Pigeon> findByCode(String code);
}
