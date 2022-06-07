package com.itmo.commands;

import com.itmo.accounts.AbstractAccount;
import com.itmo.tools.BanksException;

public class WithdrawCommand implements AbstractCommand {

        private final AbstractAccount account;
        private final double fundsAmount;

    public WithdrawCommand(AbstractAccount account, double fundsAmount) {
        this.account = account;
        this.fundsAmount = fundsAmount;
    }

    public void execute() throws BanksException {
            account.withdrawFunds(fundsAmount);
        }

        public void undo() {
            account.addFunds(fundsAmount);
        }
}
