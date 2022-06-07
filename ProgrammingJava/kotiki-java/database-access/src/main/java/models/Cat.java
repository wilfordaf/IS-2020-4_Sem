package models;

import models.enums.CatColor;
import tools.CatsException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "cats")
public class Cat {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Basic
    @Column(name = "breed")
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private CatColor color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "owner_cats",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "owner_id")
    )
    private Owner owner;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name="cat_friends",
            joinColumns=@JoinColumn(name="cat_id"),
            inverseJoinColumns=@JoinColumn(name="friend_id")
    )
    private Set<Cat> friends = new HashSet<>();

    @ManyToMany(mappedBy = "friends", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Cat> friendOf = new HashSet<>();

    public Cat() {}

    public Cat(String name, LocalDate birthDate, String breed, CatColor color) {
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
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

    public Set<Cat> getFriends() {
        return friends;
    }

    public void setFriends(Set<Cat> friends) {
        this.friends = friends;
    }

    public Set<Cat> getFriendOf() {
        return friendOf;
    }

    public void setFriendOf(Set<Cat> friendOf) {
        this.friendOf = friendOf;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void addFriend(Cat cat) throws CatsException {
        if (equals(cat)) {
            throw new CatsException("Cat cannot make friends with himself");
        }

        friendOf.add(cat);
        friends.add(cat);
        cat.getFriends().add(this);
        cat.getFriendOf().add(this);
    }

    public void removeFriend(Cat cat) throws CatsException {
        if (!getFriends().contains(cat)) {
            throw new CatsException("Cat does not have such friend");
        }

        friendOf.remove(cat);
        friends.remove(cat);
        cat.getFriends().remove(this);
        cat.getFriendOf().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return id == cat.id && Objects.equals(name, cat.name) && Objects.equals(birthDate, cat.birthDate) && Objects.equals(breed, cat.breed) && Objects.equals(color, cat.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, breed, color);
    }
}
