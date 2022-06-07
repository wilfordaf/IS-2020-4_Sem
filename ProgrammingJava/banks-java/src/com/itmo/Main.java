package com.itmo;

import com.itmo.builders.CustomerBuilder;
import com.itmo.console_interface.ConsoleInterface;
import com.itmo.interfaces.AbstractBank;
import com.itmo.interfaces.AbstractCentralBank;
import com.itmo.models.Bank;
import com.itmo.models.CentralBank;
import com.itmo.tools.BanksException;

public class Main {

    public static void main(String[] args) throws BanksException {
        AbstractCentralBank centralBank = new CentralBank();
        AbstractBank bank = new Bank(
                100000,
                10000,
                5,
                50000,
                500);
        centralBank.addBank(bank);
        var customer= new CustomerBuilder().
                setName("Sergey", "Yurpalov").
                setAddress("123").
                setPassport("123").
                build();

        new ConsoleInterface(centralBank, bank, customer).start();
    }
}
