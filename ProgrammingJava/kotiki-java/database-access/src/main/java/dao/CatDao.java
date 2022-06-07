package dao;

import models.Cat;
import models.Owner;
import models.enums.CatColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CatDao extends JpaRepository<Cat, Integer> {

    List<Cat> findByOwner(Owner owner);
    List<Cat> findByName(String name);
    List<Cat> findByNameAndOwner(String name, Owner owner);
    List<Cat> findByBirthDate(LocalDate birthDate);
    List<Cat> findByBirthDateAndOwner(LocalDate birthDate, Owner owner);
    List<Cat> findByBreed(String breed);
    List<Cat> findByBreedAndOwner(String breed, Owner owner);
    List<Cat> findByColor(CatColor color);
    List<Cat> findByColorAndOwner(CatColor color, Owner owner);
}
