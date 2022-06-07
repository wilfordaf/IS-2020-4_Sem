package com.itmo.models;

import com.itmo.interfaces.AbstractCustomer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer implements AbstractCustomer {

    private String firstName;
    private String lastName;
    private String address;
    private String passport;

    private final List<String> notifications = new ArrayList<>();
    private final List<UUID> accountIds = new ArrayList<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public List<UUID> getAccountIds() {
        return accountIds;
    }

    public boolean hasName() {
        return !(firstName == null || lastName == null ||
                firstName.isBlank() || lastName.isBlank());
    }

    public boolean isVerified() {
        return !(address == null || passport == null ||
                firstName.isBlank() || lastName.isBlank());
    }

    public void update(String message) {
        notifications.add(message);
    }

    public void clearNotifications(){
        notifications.clear();
    }
}
