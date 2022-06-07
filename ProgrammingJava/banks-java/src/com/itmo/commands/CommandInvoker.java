package com.itmo.commands;

import com.itmo.tools.BanksException;

public class CommandInvoker {

    private AbstractCommand currentCommand;

    public void setCommand(AbstractCommand command)
    {
        currentCommand = command;
    }

    public void runCommand() throws BanksException {
        if (currentCommand == null) {
            throw new BanksException("No command is chosen to run");
        }

        currentCommand.execute();
        refresh();
    }

    public void undoCommand() throws BanksException {
        if (currentCommand == null) {
            throw new BanksException("No command is chosen be undone");
        }

        currentCommand.undo();
        refresh();
    }

    private void refresh() {
        currentCommand = null;
    }
}
