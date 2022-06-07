package dao;

import models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OwnerDao extends JpaRepository<Owner, Integer> {

    List<Owner> findByName(String name);
    List<Owner> findByNameAndId(String name, int id);
    List<Owner> findByBirthDate(LocalDate birthDate);
    List<Owner> findByBirthDateAndId(LocalDate birthDate, int id);
}
