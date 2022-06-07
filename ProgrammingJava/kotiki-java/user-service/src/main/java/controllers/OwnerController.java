package controllers;

import dto.OwnerDto;
import dto.mappers.DtoToOwnerMapper;
import dto.mappers.OwnerToDtoMapper;
import models.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.OwnerService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;
    private final OwnerToDtoMapper ownerToDtoMapper;
    private final DtoToOwnerMapper dtoToOwnerMapper;

    @Autowired
    public OwnerController(OwnerService ownerService, OwnerToDtoMapper ownerToDtoMapper, DtoToOwnerMapper dtoToOwnerMapper) {
        this.ownerService = ownerService;
        this.ownerToDtoMapper = ownerToDtoMapper;
        this.dtoToOwnerMapper = dtoToOwnerMapper;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<OwnerDto> getById(@PathVariable int id) {
        try {
            Owner owner = ownerService.getById(id);
            return ResponseEntity.ok(ownerToDtoMapper.convert(owner));
        } catch (EntityNotFoundException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public void save(@RequestBody OwnerDto ownerDto) {
        ownerService.save(dtoToOwnerMapper.convert(ownerDto));
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable int id) {
        try {
            ownerService.deleteById(id);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping ("/find-all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OwnerDto> findAll() {
        return ownerService.findAll().stream().map(ownerToDtoMapper::convert).toList();
    }

    @GetMapping ("/find-by-name/{name}")
    public List<OwnerDto> findByName(@PathVariable String name) {
        return ownerService.findByName(name).stream().map(ownerToDtoMapper::convert).toList();
    }

    @GetMapping ("/find-by-birthDate/{birthDate}")
    public List<OwnerDto> findByBirthDate(@PathVariable String birthDate) {
        return ownerService.findByBirthDate(birthDate).stream().map(ownerToDtoMapper::convert).toList();
    }
}
