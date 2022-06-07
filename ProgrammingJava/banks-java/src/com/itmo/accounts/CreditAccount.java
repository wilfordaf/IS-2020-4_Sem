package com.itmo.accounts;

import java.util.UUID;

import com.itmo.interfaces.AbstractCustomer;
import com.itmo.tools.BanksException;

public class CreditAccount implements AbstractAccount {

    private AbstractCustomer owner;

    private double funds;

    private final UUID id = UUID.randomUUID();
    
    private double transactionLimit;
    private double creditCommissionSum;

    private double transactionLimitForRegisteredCustomer;
    private double transactionLimitForUnregisteredCustomer;

    private double fee;
    private double creditAmount;

    public CreditAccount(AbstractCustomer owner,
                         double transactionLimitForRegisteredCustomer,
                         double transactionLimitForUnregisteredCustomer,
                         double fee,
                         double creditAmount) {
        this.owner = owner;
        this.transactionLimitForRegisteredCustomer = transactionLimitForRegisteredCustomer;
        this.transactionLimitForUnregisteredCustomer = transactionLimitForUnregisteredCustomer;
        this.fee = fee;
        this.creditAmount = creditAmount;
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

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public double getFunds() {
        return Math.round(funds);
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public AccountTypes getType(){
        return AccountTypes.CREDIT_ACCOUNT;
    }

    public UUID getId() {
        return id;
    }

    public void addFunds(double amount) {
        funds += amount;
    }

    public void withdrawFunds(double amount) throws BanksException {
        if (amount >= transactionLimit) {
            throw new BanksException("Transaction limit of the account was exceeded");
        }

        if (funds + creditAmount < amount) {
            throw new BanksException("Insufficient funds");
        }

        funds -= amount;
    }

    public void notifyOwner(String message) {
        owner.update(message);
    }

    public void updateDay() {
        if (funds < 0) {
            creditCommissionSum += fee;
        }
    }

    public void updateMonth() {
        funds -= creditCommissionSum;
        creditCommissionSum = 0;
    }

    public void updateTransactionLimit() {
        setTransactionLimit();
    }

    public void changeCreditLimit(double creditLimit) throws BanksException {
        if (creditLimit <= 0) {
            throw new BanksException("Credit limit cannot be negative");
        }

        creditAmount = creditLimit;
    }

    public void changeCreditFee(double creditFee) throws BanksException {
        if (creditFee <= 0) {
            throw new BanksException("Credit fee cannot be negative");
        }

        fee = creditFee;
    }

    private void setTransactionLimit() {
        if (owner.isVerified()){
            transactionLimit = transactionLimitForRegisteredCustomer;
            return;
        }

        transactionLimit = transactionLimitForUnregisteredCustomer;
    }
}
