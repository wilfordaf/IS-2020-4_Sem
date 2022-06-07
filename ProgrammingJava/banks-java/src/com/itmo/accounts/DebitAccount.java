package com.itmo.accounts;

import java.util.UUID;

import com.itmo.interfaces.AbstractCustomer;
import com.itmo.tools.BanksException;
import com.itmo.tools.Config;

public class DebitAccount implements AbstractAccount {

    private AbstractCustomer owner;

    private double funds;

    private final UUID id = UUID.randomUUID();

    private double transactionLimit;

    private double interestFunds;

    private double transactionLimitForRegisteredCustomer;
    private double transactionLimitForUnregisteredCustomer;

    private double annualInterestOnBalance;

    public DebitAccount(double transactionLimitForRegisteredCustomer,
                        double transactionLimitForUnregisteredCustomer,
                        double annualInterestOnBalance,
                        AbstractCustomer owner) {
        this.transactionLimitForRegisteredCustomer = transactionLimitForRegisteredCustomer;
        this.transactionLimitForUnregisteredCustomer = transactionLimitForUnregisteredCustomer;
        this.annualInterestOnBalance = annualInterestOnBalance;
        this.owner = owner;
        owner.getAccountIds().add(getId());
        setTransactionLimit();
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

    public double getAnnualInterestOnBalance() {
        return annualInterestOnBalance;
    }

    public void setAnnualInterestOnBalance(double annualInterestOnBalance) {
        this.annualInterestOnBalance = annualInterestOnBalance;
    }

    public double getFunds() {
        return Math.round(funds);
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public AccountTypes getType(){
        return AccountTypes.DEBIT_ACCOUNT;
    }

    public UUID getId() {
        return id;
    }

    public void addFunds(double amount) {
        funds += amount;
    }

    public void withdrawFunds(double amount) throws BanksException {
        if (funds <= 0) {
            throw new BanksException("Insufficient funds");
        }

        if (amount >= transactionLimit) {
            throw new BanksException("Transaction limit of the account was exceeded");
        }

        if (funds < amount) {
            throw new BanksException("Insufficient funds");
        }

        funds -= amount;
    }

    public void calculateInterest() {
        interestFunds += annualInterestOnBalance /
                Config.PERCENT_DIVIDER /
                Config.DAYS_IN_YEAR * funds;
    }

    public void notifyOwner(String message) {
        owner.update(message);
    }

    public void updateDay() {
        calculateInterest();
    }

    public void updateMonth() {
        funds += interestFunds;
        interestFunds = 0;
    }

    public void updateTransactionLimit() {
        setTransactionLimit();
    }

    public void changeAnnualInterest(double interest) throws BanksException {
        if (interest <= 0) {
            throw new BanksException("Annual interest cannot be negative");
        }

        annualInterestOnBalance = interest;
    }

    private void setTransactionLimit() {
        if (owner.isVerified()){
            transactionLimit = transactionLimitForRegisteredCustomer;
            return;
        }

        transactionLimit = transactionLimitForUnregisteredCustomer;
    }
}
