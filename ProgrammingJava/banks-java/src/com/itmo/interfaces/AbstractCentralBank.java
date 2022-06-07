package com.itmo.interfaces;

import com.itmo.tools.BanksException;

public interface AbstractCentralBank {

    void addBank(AbstractBank bank);

    void removeBank(AbstractBank bank) throws BanksException;

    void setNewTransactionLimitForUnregisteredCustomer(double limit, AbstractBank bank);

    void setNewTransactionLimitForRegisteredCustomer(double limit, AbstractBank bank);

    void setNewDebitAnnualInterestOnBalance(double interest, AbstractBank bank);

    void setNewCreditLimitAmount(double limit, AbstractBank bank);

    void setNewCreditFee(double fee, AbstractBank bank);

    void notifyBanksDayPassed();

    void notifyBanksMonthPassed();
}
