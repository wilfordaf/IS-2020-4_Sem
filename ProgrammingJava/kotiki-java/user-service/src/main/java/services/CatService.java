package services;

import dao.CatDao;
import models.Cat;
import models.Owner;
import models.User;
import models.enums.Authority;
import models.enums.CatColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CatService {

    private final CatDao catDao;
    private final UserService userService;

    @Autowired
    public CatService(CatDao catDao, UserService userService) {
        this.catDao = catDao;
        this.userService = userService;
    }

    public Cat getById(int id) {
        Cat cat = catDao.getById(id);
        User user = userService.getAuthorizedUser();
        if (user.getRole() == Authority.ROLE_ADMIN) {
            return cat;
        }

        Owner owner = user.getOwner();
        if (owner == cat.getOwner()) {
            return cat;
        }

        return null;
    }

    public void save(Cat cat) {
        User user = userService.getAuthorizedUser();
        if (user.getRole() == Authority.ROLE_ADMIN) {
            catDao.save(cat);
        }

        Owner owner = user.getOwner();
        if (owner.getId() == cat.getOwner().getId()) {
            catDao.save(cat);
        }
    }

    public void deleteById(int id)  {
        catDao.deleteById(id);
    }

    public List<Cat> findAll() {
        User user = userService.getAuthorizedUser();
        if (user.getRole() == Authority.ROLE_ADMIN) {
            return catDao.findAll();
        }

        return catDao.findByOwner(user.getOwner());
    }

    public List<Cat> findByName(String name) {
        User user = userService.getAuthorizedUser();
        if (user.getRole() == Authority.ROLE_ADMIN) {
            return catDao.findByName(name);
        }

        return catDao.findByNameAndOwner(name, user.getOwner());

    }

    public List<Cat> findByBirthDate(String birthDate) {
        LocalDate date = LocalDate.parse(birthDate);
        User user = userService.getAuthorizedUser();
        if (user.getRole() == Authority.ROLE_ADMIN) {
            return catDao.findByBirthDate(date);
        }

        return catDao.findByBirthDateAndOwner(date, user.getOwner());

    }

    public List<Cat> findByBreed(String breed) {
        User user = userService.getAuthorizedUser();
        if (user.getRole() == Authority.ROLE_ADMIN) {
            return catDao.findByBreed(breed);
        }

        return catDao.findByBreedAndOwner(breed, user.getOwner());
    }

    public List<Cat> findByColor(CatColor color) {
        User user = userService.getAuthorizedUser();
        if (user.getRole() == Authority.ROLE_ADMIN) {
            return catDao.findByColor(color);
        }

        return catDao.findByColorAndOwner(color, user.getOwner());
    }
}
