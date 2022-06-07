package com.itmo.commands;

import com.itmo.tools.BanksException;

public interface AbstractCommand {

    void execute() throws BanksException;

    void undo() throws BanksException;
}
