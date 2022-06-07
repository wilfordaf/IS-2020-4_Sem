package com.itmo.commands;

import com.itmo.accounts.AbstractAccount;
import com.itmo.tools.BanksException;

public class TransferCommand implements AbstractCommand {

    private final AbstractAccount accountToWithdraw;
    private final AbstractAccount accountToAdd;
    private final double fundsAmount;

    public TransferCommand(AbstractAccount accountToWithdraw, AbstractAccount accountToAdd, double fundsAmount) {
        this.accountToWithdraw = accountToWithdraw;
        this.accountToAdd = accountToAdd;
        this.fundsAmount = fundsAmount;
    }

    public void execute() throws BanksException {
        accountToWithdraw.withdrawFunds(fundsAmount);
        accountToAdd.addFunds(fundsAmount);
    }

    public void undo() throws BanksException {
        accountToWithdraw.addFunds(fundsAmount);
        accountToAdd.withdrawFunds(fundsAmount);
    }
}
