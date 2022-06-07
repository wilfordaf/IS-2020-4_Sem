package com.itmo.accounts;

import com.itmo.interfaces.AbstractCustomer;
import com.itmo.tools.BanksException;

import java.util.UUID;

public interface AbstractAccount {

    UUID getId();

    double getFunds();

    AbstractCustomer getOwner();

    AccountTypes getType();

    double getTransactionLimitForRegisteredCustomer();
    void setTransactionLimitForRegisteredCustomer(double limit);

    double getTransactionLimitForUnregisteredCustomer();
    void setTransactionLimitForUnregisteredCustomer(double limit);

    void addFunds(double amount);

    void withdrawFunds(double amount) throws BanksException;

    void notifyOwner(String message);

    void updateDay();

    void updateMonth();

    void updateTransactionLimit();
}
