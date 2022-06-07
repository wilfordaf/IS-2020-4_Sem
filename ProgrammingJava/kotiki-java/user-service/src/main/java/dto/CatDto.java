package dto;

import models.enums.CatColor;

import java.time.LocalDate;
import java.util.*;

public class CatDto {

    private int id;

    private String name;

    private LocalDate birthDate;

    private String breed;

    private CatColor color;

    private OwnerDto ownerDto;

    private Set<Integer> friends;

    public CatDto() {
        friends = new HashSet<>();
    }

    public CatDto(int id, String name, LocalDate birthDate, String breed, CatColor color, OwnerDto ownerDto, Set<Integer> friends) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
        this.ownerDto = ownerDto;
        this.friends = friends;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public CatColor getColor() {
        return color;
    }

    public void setColor(CatColor color) {
        this.color = color;
    }

    public OwnerDto getOwner() {
        return ownerDto;
    }

    public void setOwner(OwnerDto ownerDto) {
        this.ownerDto = ownerDto;
    }

    public Set<Integer> getFriends() {
        return friends;
    }

    public void setFriends(Set<Integer> friends) {
        this.friends = friends;
    }
}