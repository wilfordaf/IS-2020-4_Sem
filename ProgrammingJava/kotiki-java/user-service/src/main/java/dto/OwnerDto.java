package dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class OwnerDto {

    private int id;

    private String name;

    private LocalDate birthDate;

    private Set<Integer> cats;

    public OwnerDto() {
        cats = new HashSet<>();
    }

    public OwnerDto(int id, String name, LocalDate birthDate, Set<Integer> cats) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.cats = cats;
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

    public Set<Integer> getCats() {
        return cats;
    }

    public void setCats(Set<Integer> cats) {
        this.cats = cats;
    }
}
