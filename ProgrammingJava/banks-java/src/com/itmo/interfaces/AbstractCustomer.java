package com.itmo.interfaces;

import java.util.List;
import java.util.UUID;

public interface AbstractCustomer {
    
    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getAddress();

    void setAddress(String address);

    String getPassport();

    void setPassport(String passport);

    List<String> getNotifications();

    List<UUID> getAccountIds();

    boolean hasName();

    boolean isVerified();

    void update(String message);

    void clearNotifications();
}
