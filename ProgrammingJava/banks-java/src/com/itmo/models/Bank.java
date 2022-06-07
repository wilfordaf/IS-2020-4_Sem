package com.itmo.models;

import com.itmo.accounts.*;
import com.itmo.commands.*;
import com.itmo.interfaces.AbstractBank;
import com.itmo.interfaces.AbstractCustomer;
import com.itmo.tools.BanksException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Bank implements AbstractBank {

    private final List<AbstractAccount> accounts = new ArrayList<>();
    private final CommandInvoker invoker = new CommandInvoker();

    private double transactionLimitForRegisteredCustomer;
    private double transactionLimitForUnregisteredCustomer;
    private double debitAnnualInterestOnBalance;
    private double creditLimitAmount;
    private double creditFee;

    public Bank(double transactionLimitForRegisteredCustomer,
                double transactionLimitForUnregisteredCustomer,
                double debitAnnualInterestOnBalance,
                double creditLimitAmount,
                double creditFee) {
        this.transactionLimitForRegisteredCustomer = transactionLimitForRegisteredCustomer;
        this.transactionLimitForUnregisteredCustomer = transactionLimitForUnregisteredCustomer;
        this.debitAnnualInterestOnBalance = debitAnnualInterestOnBalance;
        this.creditLimitAmount = creditLimitAmount;
        this.creditFee = creditFee;
    }
    
    public double getTransactionLimitForRegisteredCustomer() {
        return transactionLimitForRegisteredCustomer;
    }

    public void setTransactionLimitForRegisteredCustomer(double limit) {
        transactionLimitForRegisteredCustomer = limit;
    }
    
    public double getTransactionLimitForUnregisteredCustomer() {
        return transactionLimitForUnregisteredCustomer;
    }

    public void setTransactionLimitForUnregisteredCustomer(double limit) {
        transactionLimitForUnregisteredCustomer = limit;
    }
    
    public double getDebitAnnualInterestOnBalance() {
        return debitAnnualInterestOnBalance;
    }

    public void setDebitAnnualInterestOnBalance(double interest) {
        debitAnnualInterestOnBalance = interest;
    }
    
    public double getCreditLimitAmount() {
        return creditLimitAmount;
    }

    public void setCreditLimitAmount(double limit) {
        creditLimitAmount = limit;
    }
    
    public double getCreditFee() {
        return creditFee;
    }

    public void setCreditFee(double fee) {
        creditFee = fee;
    }
    
    public DebitAccount createDebitAccount(AbstractCustomer customer) {
        var newAccount = new DebitAccount(
                transactionLimitForRegisteredCustomer,
                transactionLimitForUnregisteredCustomer,
                debitAnnualInterestOnBalance,
                customer);

        accounts.add(newAccount);
        return newAccount;
    }

    public CreditAccount createCreditAccount(AbstractCustomer customer) {
        var newAccount = new CreditAccount(
                customer,
                transactionLimitForRegisteredCustomer,
                transactionLimitForUnregisteredCustomer,
                creditFee,
                creditLimitAmount);

        accounts.add(newAccount);
        return newAccount;
    }

    public DepositAccount createDepositAccount(
            AbstractCustomer customer,
            int daysToExpire,
            double startFunds) {
        var newAccount = new DepositAccount(
                transactionLimitForRegisteredCustomer,
                transactionLimitForUnregisteredCustomer,
                startFunds,
                daysToExpire,
                customer);

        accounts.add(newAccount);
        return newAccount;
    }

    public void removeAccount(AbstractAccount account) throws BanksException {
        if (!accounts.remove(account)) {
            throw new BanksException("Account to delete was not found");
        }
    }

    public void notifyAllCustomers(String message) {
        accounts.forEach(a -> a.notifyOwner(message));
    }

    public void notifySpecificCustomers(AccountTypes accountType, String message) {
        accounts.forEach(account -> {
            if (account.getType() == accountType) {
                account.notifyOwner(message);
            }
        });
    }

    public void updateDay() {
        accounts.forEach(AbstractAccount::updateDay);
    }

    public void updateMonth() {
        accounts.forEach(AbstractAccount::updateMonth);
    }

    public AddCommand addFundsToAccount(AbstractAccount account, double funds) throws BanksException {
        AddCommand addCommand = new AddCommand(account, funds);
        invoker.setCommand(addCommand);
        invoker.runCommand();
        return addCommand;
    }

    public WithdrawCommand withdrawFundsFromAccount(AbstractAccount account, double funds) throws BanksException {
        WithdrawCommand withdrawCommand = new WithdrawCommand(account, funds);
        invoker.setCommand(withdrawCommand);
        invoker.runCommand();
        return withdrawCommand;
    }

    public TransferCommand transferFundsBetweenAccounts(
            AbstractAccount accountToWithdraw,
            AbstractAccount accountToAdd,
            double funds) throws BanksException {
        TransferCommand transferCommand = new TransferCommand(accountToWithdraw, accountToAdd, funds);
        invoker.setCommand(transferCommand);
        invoker.runCommand();
        return transferCommand;
    }

    public void undoCommand(AbstractCommand command) throws BanksException {
        invoker.setCommand(command);
        invoker.undoCommand();
    }

    public AbstractAccount getAccountById(String id) {
        return accounts.stream().filter(account ->
                Objects.equals(account.getId().toString(), id)).findFirst().orElse(null);
    }

    public List<AbstractAccount> getAllCustomerAccounts(AbstractCustomer customer) {
        return accounts.stream().filter(account -> account.getOwner() == customer).collect(Collectors.toList());
    }

    public void verifyCustomer(AbstractCustomer customer, String newPassport, String newAddress) {
        customer.setAddress(newAddress);
        customer.setPassport(newPassport);
        customer.getAccountIds().forEach(id -> getAccountById(id.toString()).updateTransactionLimit());
    }

    public void changeTransactionLimitForRegisteredCustomer(double limit) {
        transactionLimitForRegisteredCustomer = limit;
        accounts.forEach(account -> account.setTransactionLimitForRegisteredCustomer(limit));
    }

    public void changeTransactionLimitForUnregisteredCustomer(double limit) {
        transactionLimitForUnregisteredCustomer = limit;
        accounts.forEach(account -> account.setTransactionLimitForUnregisteredCustomer(limit));
    }

    public void changeDebitAnnualInterestOnBalance(double interest) {
        debitAnnualInterestOnBalance = interest;
        accounts.forEach(account -> {
            if (account instanceof DebitAccount debitAccount) {
                try {
                    debitAccount.changeAnnualInterest(interest);
                } catch (BanksException ignored) {}
            }
        });
    }

    public void changeCreditLimitAmount(double limit) {
        creditLimitAmount = limit;
        accounts.forEach(account -> {
            if (account instanceof CreditAccount creditAccount) {
                try {
                    creditAccount.changeCreditLimit(limit);
                } catch (BanksException ignored) {}
            }
        });
    }

    public void changeCreditFee(double fee) {
        creditFee = fee;
        accounts.forEach(account -> {
            if (account instanceof CreditAccount creditAccount) {
                try {
                    creditAccount.changeCreditFee(fee);
                } catch (BanksException ignored) {}
            }
        });
    }
}
