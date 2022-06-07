package com.itmo.commands;

import com.itmo.accounts.AbstractAccount;
import com.itmo.tools.BanksException;

public class AddCommand implements AbstractCommand{

    private final AbstractAccount account;
    private final double fundsAmount;

    public AddCommand(AbstractAccount account, double fundsAmount) {
        this.account = account;
        this.fundsAmount = fundsAmount;
    }

    public void execute() {
        account.addFunds(fundsAmount);
    }

    public void undo() throws BanksException {
        account.withdrawFunds(fundsAmount);
    }
}
