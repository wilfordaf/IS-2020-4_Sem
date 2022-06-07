package dto;

import models.enums.Authority;

public class UserDto {

    private int id;

    private String username;

    private String password;

    private Authority role;

    private OwnerDto owner;

    public UserDto(int id, String username, String password, Authority role, OwnerDto owner) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.owner = owner;
    }

    public UserDto() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Authority getRole() {
        return role;
    }

    public void setRole(Authority role) {
        this.role = role;
    }

    public OwnerDto getOwner() {
        return owner;
    }

    public void setOwner(OwnerDto owner) {
        this.owner = owner;
    }
}
