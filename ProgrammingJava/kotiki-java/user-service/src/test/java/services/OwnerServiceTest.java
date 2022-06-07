package services;

import dao.OwnerDao;
import models.Cat;
import models.Owner;
import models.enums.CatColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OwnerServiceTest {

    private OwnerDao ownerDao;
    private OwnerService ownerService;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        ownerDao = Mockito.mock(OwnerDao.class);
        userService = Mockito.mock(UserService.class);
        ownerService = new OwnerService(ownerDao, userService);
    }

    @Test
    public void getOwner() {
        var mockedOwner = new Owner("owner", LocalDate.now());
        when(ownerDao.getById(1)).thenReturn(mockedOwner);
        var owner = ownerService.getById(1);
        verify(ownerDao).getById(1);
        Assertions.assertEquals(mockedOwner, owner);
    }

    @Test
    public void updateOwner() {
        var mockedOwner = new Owner("owner", LocalDate.now());
        var mockedCat = new Cat("cat2", LocalDate.now(), "breed", CatColor.BLACK);

        when(ownerDao.getById(1)).thenReturn(mockedOwner);

        var owner = ownerService.getById(1);

        Assertions.assertEquals(0, owner.getCats().size());

        mockedOwner.addCat(mockedCat);

        ownerService.save(owner);
        verify(ownerDao).save(owner);

        Assertions.assertEquals(1, owner.getCats().size());
    }

    @Test
    public void findAllOwners() {
        var mockedOwner1 = new Owner("owner1", LocalDate.now());
        var mockedOwner2 = new Owner("owner2", LocalDate.now());
        List<Owner> mockedOwners = new ArrayList<>();

        mockedOwners.add(mockedOwner1);
        mockedOwners.add(mockedOwner2);

        when(ownerDao.findAll()).thenReturn(mockedOwners);

        var ownerList = ownerService.findAll();
        verify(ownerDao).findAll();

        Assertions.assertEquals(mockedOwners, ownerList);
    }

    @Test
    public void deleteOwner() {
        when(ownerDao.getById(1)).thenReturn(null);

        ownerService.deleteById(1);
        verify(ownerDao).deleteById(1);

        Assertions.assertNull(ownerService.getById(1));
    }
}
