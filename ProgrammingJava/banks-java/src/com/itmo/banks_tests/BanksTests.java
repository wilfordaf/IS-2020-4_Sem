package com.itmo.banks_tests;

import com.itmo.accounts.CreditAccount;
import com.itmo.accounts.DebitAccount;
import com.itmo.accounts.DepositAccount;
import com.itmo.builders.CustomerBuilder;
import com.itmo.commands.AddCommand;
import com.itmo.interfaces.AbstractBank;
import com.itmo.interfaces.AbstractCentralBank;
import com.itmo.models.Bank;
import com.itmo.models.CentralBank;
import com.itmo.models.Customer;
import com.itmo.models.TimeManager;
import com.itmo.tools.BanksException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BanksTests {
    private static final AbstractCentralBank MyCentralBank = new CentralBank();
    private static final AbstractBank MyBank = new Bank(100000,
            10000,
            5,
            50000,
            500);

    private final TimeManager timeManager = TimeManager.getTimeManager(MyCentralBank);

    @Test
    public void WithdrawFromDepositBeforeExpiration_RewindTime_WithdrawAfterExpiration_ThrowException() throws BanksException {
        MyCentralBank.addBank(MyBank);
        Customer customer = new CustomerBuilder().
                setName("Sergey", "Yurpalov").
                setPassport("123").
                setAddress("123").
                build();

        DepositAccount depositAccount = MyBank.createDepositAccount(customer, 10, 75000);
        Assert.assertThrows(BanksException.class, () -> {
            MyBank.withdrawFundsFromAccount(depositAccount, 10000);
        });

        timeManager.dissipateDays(11);
        MyBank.withdrawFundsFromAccount(depositAccount, 10000);
        Assert.assertEquals(depositAccount.getFunds(), 65000);
    }

    @Test
    public void CreateAccounts_UseTransactionCommands_UndoTransactionCommands_RewindTimeForDividends() throws BanksException {
        MyCentralBank.addBank(MyBank);
        Customer customer1 = new CustomerBuilder().
                setName("Sergey", "Yurpalov").
                build();

        Customer customer2 = new CustomerBuilder().
                setName("Vlad", "Povish").
                setAddress("1234").
                build();

        Customer customer3 = new CustomerBuilder().
                setName("123", "123").
                setAddress("1234").
                setPassport("1234")
                .build();

        DebitAccount debitAccount = MyBank.createDebitAccount(customer1);
        CreditAccount creditAccount = MyBank.createCreditAccount(customer2);
        DepositAccount depositAccount = MyBank.createDepositAccount(customer3, 10, 75000);

        AddCommand addCommand1 = MyBank.addFundsToAccount(debitAccount, 5000);
        Assert.assertEquals(debitAccount.getFunds(), 5000);
        MyBank.undoCommand(addCommand1);
        Assert.assertEquals(debitAccount.getFunds(), 0);

        MyBank.addFundsToAccount(debitAccount, 5000);
        MyBank.transferFundsBetweenAccounts(debitAccount, creditAccount, 2500);
        Assert.assertEquals(creditAccount.getFunds(), 2500);
        MyBank.withdrawFundsFromAccount(creditAccount, 9000);

        timeManager.dissipateMonths(1);
        Assert.assertEquals(creditAccount.getFunds(), -21500);
        Assert.assertEquals(Math.round(depositAccount.getFunds()), 75216);
        Assert.assertEquals(Math.round(debitAccount.getFunds()), 2510);
    }

    @Test
    public void SurpassTransactionLimit_RegisterUser_SatisfyTransactionLimit_ThrowException() throws BanksException {
        MyCentralBank.addBank(MyBank);
        Customer customer1 = new CustomerBuilder().
                setName("Sergey", "Yurpalov").
                build();

        DebitAccount debitAccount = MyBank.createDebitAccount(customer1);
        MyBank.addFundsToAccount(debitAccount, 20000);
        Assert.assertThrows(BanksException.class, () -> {
            MyBank.withdrawFundsFromAccount(debitAccount, 10000);
        });

        MyBank.verifyCustomer(customer1, "123", "123");
        MyBank.withdrawFundsFromAccount(debitAccount, 10000);
        Assert.assertEquals(debitAccount.getFunds(), 10000);
    }

    @Test
    public void SurpassCreditLimit_ChangeCreditLimitForBank_SatisfyCreditLimit_ThrowException() throws BanksException {
        MyCentralBank.addBank(MyBank);
        Customer customer1 = new CustomerBuilder().
                setName("Sergey", "Yurpalov").
                setPassport("123").
                setAddress("123").
                build();

        CreditAccount creditAccount = MyBank.createCreditAccount(customer1);

        Assert.assertThrows(BanksException.class, () -> {
            MyBank.withdrawFundsFromAccount(creditAccount, 55000);
        });

        MyCentralBank.setNewCreditLimitAmount(60000, MyBank);
        MyBank.withdrawFundsFromAccount(creditAccount, 55000);
        Assert.assertEquals(creditAccount.getFunds(), -55000);
    }
}
