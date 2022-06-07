package controllers;

import dto.CatDto;
import dto.mappers.CatToDtoMapper;
import dto.mappers.DtoToCatMapper;
import models.Cat;
import models.enums.CatColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.CatService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/cats")
public class CatController {

    private final CatService catService;
    private final CatToDtoMapper catToDtoMapper;
    private final DtoToCatMapper dtoToCatMapper;

    @Autowired
    public CatController(CatService catService, CatToDtoMapper catToDtoMapper, DtoToCatMapper dtoToCatMapper) {
        this.catService = catService;
        this.catToDtoMapper = catToDtoMapper;
        this.dtoToCatMapper = dtoToCatMapper;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CatDto> getById(@PathVariable int id) {
        try {
            Cat cat = catService.getById(id);
            return ResponseEntity.ok(catToDtoMapper.convert(cat));

        } catch (EntityNotFoundException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public void save(@RequestBody CatDto catDto) {
        catService.save(dtoToCatMapper.convert(catDto));
    }

    @PostMapping("/delete/{id}")
    public void deleteById(@PathVariable int id) {
        try {
            catService.deleteById(id);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping ("/find-all")
    public List<CatDto> findAll() {
        return catService.findAll().stream().map(catToDtoMapper::convert).toList();
    }

    @GetMapping("/find-by-name/{name}")
    public List<CatDto> findByName(@PathVariable String name) {
        return catService.findByName(name).stream().map(catToDtoMapper::convert).toList();
    }

    @GetMapping("/find-by-birthDate/{birthDate}")
    public List<CatDto> findByBirthDate(@PathVariable String birthDate) {
        return catService.findByBirthDate(birthDate).stream().map(catToDtoMapper::convert).toList();
    }

    @GetMapping("/find-by-breed/{breed}")
    public List<CatDto> findByBreed(@PathVariable String breed) {
        return catService.findByBreed(breed).stream().map(catToDtoMapper::convert).toList();
    }

    @GetMapping("/find-by-color/{color}")
    public List<CatDto> findByColor(@PathVariable CatColor color) {
        return catService.findByColor(color).stream().map(catToDtoMapper::convert).toList();
    }
}
