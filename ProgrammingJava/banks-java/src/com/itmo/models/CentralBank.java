package com.itmo.models;

import com.itmo.accounts.AccountTypes;
import com.itmo.interfaces.AbstractBank;
import com.itmo.interfaces.AbstractCentralBank;
import com.itmo.tools.BanksException;

import java.util.ArrayList;
import java.util.List;

public class CentralBank implements AbstractCentralBank {

    private final List<AbstractBank>  banks = new ArrayList<>();

    public void addBank(AbstractBank bank) {
        banks.add(bank);
    }

    public void removeBank(AbstractBank bank) throws BanksException {
        if (!banks.remove(bank)) {
            throw new BanksException("Bank to delete was not found");
        }
    }

    public void setNewTransactionLimitForUnregisteredCustomer(double limit, AbstractBank bank) {
        bank.changeTransactionLimitForUnregisteredCustomer(limit);
        bank.notifyAllCustomers("Transaction limit For unregistered customer was changed to " + limit);
    }

    public void setNewTransactionLimitForRegisteredCustomer(double limit, AbstractBank bank) {
        bank.changeTransactionLimitForRegisteredCustomer(limit);
        bank.notifyAllCustomers("Transaction limit For registered customer was changed to " + limit);
    }

    public void setNewDebitAnnualInterestOnBalance(double interest, AbstractBank bank) {
        bank.changeDebitAnnualInterestOnBalance(interest);
        bank.notifySpecificCustomers(
                AccountTypes.DEBIT_ACCOUNT,
                "Annual interest on balance was changed to " + interest);
    }

    public void setNewCreditLimitAmount(double limit, AbstractBank bank) {
        bank.changeCreditLimitAmount(limit);
        bank.notifySpecificCustomers(
                AccountTypes.CREDIT_ACCOUNT,
                "Credit limit amount was changed to " + limit);
    }

    public void setNewCreditFee(double fee, AbstractBank bank) {
        bank.changeCreditFee(fee);
        bank.notifySpecificCustomers(
                AccountTypes.CREDIT_ACCOUNT,
                "Credit fee was changed to " + fee);
    }

    public void notifyBanksDayPassed() {
        banks.forEach(AbstractBank::updateDay);
    }

    public void notifyBanksMonthPassed() {
        banks.forEach(AbstractBank::updateMonth);
    }
}
