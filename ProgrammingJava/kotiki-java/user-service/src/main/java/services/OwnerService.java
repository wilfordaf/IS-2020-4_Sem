package services;

import dao.OwnerDao;
import models.Owner;
import models.User;
import models.enums.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OwnerService {

    private final OwnerDao ownerDao;
    private final UserService userService;

    @Autowired
    public OwnerService(OwnerDao ownerDao, UserService userService) {
        this.ownerDao = ownerDao;
        this.userService = userService;
    }

    public Owner getById(int id) {
        User user = userService.getAuthorizedUser();
        if (user.getRole() == Authority.ROLE_ADMIN) {
            return ownerDao.getById(id);
        }

        Owner owner = user.getOwner();
        if (owner.getId() == id) {
            return owner;
        }

        return null;
    }

    public void save(Owner owner) {
        ownerDao.save(owner);
    }

    public void deleteById(int id) {
        ownerDao.deleteById(id);
    }

    public List<Owner> findAll() {
        User user = userService.getAuthorizedUser();
        if (user.getRole() == Authority.ROLE_ADMIN) {
            return ownerDao.findAll();
        }

        return List.of(user.getOwner());
    }

    public List<Owner> findByName(String name) {
        User user = userService.getAuthorizedUser();
        if (user.getRole() == Authority.ROLE_ADMIN) {
            return ownerDao.findByName(name);
        }

        Owner owner = user.getOwner();
        return ownerDao.findByNameAndId(name, owner.getId());
    }

    public List<Owner> findByBirthDate(String birthDate) {
        LocalDate date = LocalDate.parse(birthDate);
        User user = userService.getAuthorizedUser();
        if (user.getRole() == Authority.ROLE_ADMIN) {
            return ownerDao.findByBirthDate(date);
        }

        Owner owner = user.getOwner();
        return ownerDao.findByBirthDateAndId(date, owner.getId());
    }
}
