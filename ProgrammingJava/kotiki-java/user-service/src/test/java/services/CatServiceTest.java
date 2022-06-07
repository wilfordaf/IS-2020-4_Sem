package services;

import dao.CatDao;
import models.Cat;
import models.enums.CatColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import tools.CatsException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CatServiceTest {

    private CatDao catDao;
    private CatService catService;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        catDao = Mockito.mock(CatDao.class);
        userService = Mockito.mock(UserService.class);
        catService = new CatService(catDao, userService);
    }

    @Test
    public void getCat() {
        var mockedCat = new Cat("cat", LocalDate.now(), "breed", CatColor.BROWN);
        when(catDao.getById(1)).thenReturn(mockedCat);
        var cat = catService.getById(1);
        verify(catDao).getById(1);
        Assertions.assertEquals(mockedCat, cat);
    }

    @Test
    public void updateCat() throws CatsException {
        var mockedCat1 = new Cat("cat1", LocalDate.now(), "breed", CatColor.BROWN);
        var mockedCat2 = new Cat("cat2", LocalDate.now(), "breed", CatColor.BLACK);

        when(catDao.getById(1)).thenReturn(mockedCat1);
        when(catDao.getById(2)).thenReturn(mockedCat2);

        var cat1 = catService.getById(1);

        Assertions.assertEquals(0, cat1.getFriends().size());

        mockedCat1.addFriend(mockedCat2);

        catService.save(cat1);
        verify(catDao).save(cat1);

        var cat2 = catService.getById(2);

        Assertions.assertEquals(1, cat1.getFriends().size());
        Assertions.assertEquals(1, cat2.getFriends().size());
    }

    @Test
    public void findAllCats() {
        var mockedCat1 = new Cat("cat", LocalDate.now(), "breed", CatColor.BROWN);
        var mockedCat2 = new Cat("cat2", LocalDate.now(), "breed", CatColor.BLACK);
        List<Cat> mockedCats = new ArrayList<>();

        mockedCats.add(mockedCat1);
        mockedCats.add(mockedCat2);

        when(catDao.findAll()).thenReturn(mockedCats);

        var catList = catService.findAll();
        verify(catDao).findAll();

        Assertions.assertEquals(mockedCats, catList);
    }

    @Test
    public void deleteCat() {
        when(catDao.findById(1)).thenReturn(null);

        catService.deleteById(1);
        verify(catDao).deleteById(1);

        Assertions.assertNull(catService.getById(1));
    }
}
