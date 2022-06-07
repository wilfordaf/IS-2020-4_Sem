package com.itmo.interfaces;

import com.itmo.accounts.*;
import com.itmo.commands.AbstractCommand;
import com.itmo.commands.AddCommand;
import com.itmo.commands.TransferCommand;
import com.itmo.commands.WithdrawCommand;
import com.itmo.tools.BanksException;

import java.util.List;

public interface AbstractBank {

    double getTransactionLimitForRegisteredCustomer();

    double getTransactionLimitForUnregisteredCustomer();

    double getDebitAnnualInterestOnBalance();

    double getCreditLimitAmount();

    double getCreditFee();

    DebitAccount createDebitAccount(AbstractCustomer customer);

    CreditAccount createCreditAccount(AbstractCustomer customer);

    DepositAccount createDepositAccount(
            AbstractCustomer customer,
            int daysToExpire,
            double funds);

    void removeAccount(AbstractAccount account) throws BanksException;

    void notifyAllCustomers(String message);

    void notifySpecificCustomers(AccountTypes accountType, String message);

    AddCommand addFundsToAccount(AbstractAccount account, double funds) throws BanksException;

    WithdrawCommand withdrawFundsFromAccount(AbstractAccount account, double funds) throws BanksException;

    TransferCommand transferFundsBetweenAccounts(AbstractAccount accountToWithdraw, AbstractAccount accountToAdd, double funds) throws BanksException;

    void undoCommand(AbstractCommand command) throws BanksException;

    void updateDay();

    void updateMonth();

    AbstractAccount getAccountById(String id);

    List<AbstractAccount> getAllCustomerAccounts(AbstractCustomer customer);

    void verifyCustomer(AbstractCustomer customer, String newPassport, String newAddress);

    void changeTransactionLimitForRegisteredCustomer(double limit);

    void changeTransactionLimitForUnregisteredCustomer(double limit);

    void changeDebitAnnualInterestOnBalance(double interest);

    void changeCreditLimitAmount(double limit);

    void changeCreditFee(double fee);
}
