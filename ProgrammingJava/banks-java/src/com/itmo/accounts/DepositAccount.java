package com.itmo.accounts;

import com.itmo.interfaces.AbstractCustomer;
import com.itmo.tools.BanksException;
import com.itmo.tools.Config;

import java.util.UUID;

public class DepositAccount implements AbstractAccount{

    private AbstractCustomer owner;

    private double funds;

    private final UUID id = UUID.randomUUID();

    private int daysToExpire;
    private double interestFunds;

    private double transactionLimit;
    private double interestOnBalance;

    private double transactionLimitForRegisteredCustomer;
    private double transactionLimitForUnregisteredCustomer;

    public DepositAccount(double transactionLimitForRegisteredCustomer,
                          double transactionLimitForUnregisteredCustomer,
                          double funds,
                          int daysToExpire,
                          AbstractCustomer owner) {
        this.transactionLimitForRegisteredCustomer = transactionLimitForRegisteredCustomer;
        this.transactionLimitForUnregisteredCustomer = transactionLimitForUnregisteredCustomer;
        this.funds = funds;
        this.daysToExpire = daysToExpire;
        this.owner = owner;
        owner.getAccountIds().add(getId());
        setTransactionLimit();
        setInterestByStartFunds(funds);
    }

    private void setDaysToExpire(int daysToExpire) {
        this.daysToExpire = daysToExpire;
    }

    public AbstractCustomer getOwner() {
        return owner;
    }

    private void setOwner(AbstractCustomer owner) {
        this.owner = owner;
    }

    public double getTransactionLimitForRegisteredCustomer() {
        return transactionLimitForRegisteredCustomer;
    }

    public void setTransactionLimitForRegisteredCustomer(double limit) {
        this.transactionLimitForRegisteredCustomer = limit;
    }

    public double getTransactionLimitForUnregisteredCustomer() {
        return transactionLimitForUnregisteredCustomer;
    }

    public void setTransactionLimitForUnregisteredCustomer(double limit) {
        this.transactionLimitForUnregisteredCustomer = limit;
    }

    public double getFunds() {
        return Math.round(funds);
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public AccountTypes getType(){
        return AccountTypes.DEPOSIT_ACCOUNT;
    }

    public UUID getId() {
        return id;
    }

    public void addFunds(double amount)
    {
        funds += amount;
    }

    public void withdrawFunds(double amount) throws BanksException {
        if (funds <= 0) {
            throw new BanksException("Insufficient funds");
        }

        if (daysToExpire > 0) {
            throw new BanksException("Account is temporary blocked for withdrawal." + daysToExpire + "days left");
        }

        if (amount >= transactionLimit) {
            throw new BanksException("While calling an operation, you have exceeded your transaction limit.");
        }

        if (funds < amount) {
            throw new BanksException("Insufficient funds.");
        }

        funds -= amount;
    }

    public void calculateInterest()
    {
        interestFunds += interestOnBalance /
                Config.PERCENT_DIVIDER /
                Config.DAYS_IN_YEAR * funds;
    }

    public void updateDay()
    {
        calculateInterest();
        if (daysToExpire > 0)
        {
            daysToExpire--;
        }
    }

    public void updateMonth()
    {
        funds += interestFunds;
        interestFunds = 0;
    }

    public void notifyOwner(String message)
    {
        owner.update(message);
    }

    public void updateTransactionLimit()
    {
        setTransactionLimit();
    }

    private void setTransactionLimit() {
        if (owner.isVerified()){
            transactionLimit = transactionLimitForRegisteredCustomer;
            return;
        }

        transactionLimit = transactionLimitForUnregisteredCustomer;
    }

    private void setInterestByStartFunds(double funds)
    {
        if (funds <= 50000)
        {
            interestOnBalance = 3;
            return;
        }
        else if (funds <= 100000)
        {
            interestOnBalance = 3.5;
            return;
        }

        interestOnBalance = 4;
    }
}
