package com.itmo.console_interface;

import com.itmo.accounts.AbstractAccount;
import com.itmo.commands.AbstractCommand;
import com.itmo.interfaces.AbstractBank;
import com.itmo.interfaces.AbstractCentralBank;
import com.itmo.interfaces.AbstractCustomer;
import com.itmo.models.TimeManager;
import com.itmo.tools.BanksException;

import java.util.*;
import java.util.function.Consumer;

public class ConsoleInterface {

    private final Map<Commands, Consumer<String[]>> commands = new HashMap<>();
    private final AbstractBank bank;
    private final AbstractCustomer customer;
    private final AbstractCentralBank centralBank;
    private final TimeManager timeManager;

    private AbstractAccount account;
    private AbstractCommand lastExecutedCommand;

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String PURPLE = "\u001B[35m";

    public ConsoleInterface(AbstractCentralBank centralBank, AbstractBank bank, AbstractCustomer customer) {
        commands.put(Commands.PRINT_INFO_CURRENT_ACCOUNT, this::printInfoCurrentAccount);
        commands.put(Commands.ADD_DEBIT_ACCOUNT, this::addDebitAccount);
        commands.put(Commands.ADD_CREDIT_ACCOUNT, this::addCreditAccount);
        commands.put(Commands.ADD_DEPOSIT_ACCOUNT, this::addDepositAccount);
        commands.put(Commands.CHOOSE_ACCOUNT, this::chooseAccount);
        commands.put(Commands.DELETE_ACCOUNT_BY_ID, this::deleteAccountById);
        commands.put(Commands.DISSIPATE_DAYS, this::dissipateDays);
        commands.put(Commands.DISSIPATE_MONTHS, this::dissipateMonths);
        commands.put(Commands.ADD_FUNDS, this::addFunds);
        commands.put(Commands.WITHDRAW_FUNDS, this::withdrawFunds);
        commands.put(Commands.TRANSFER_FUNDS, this::transferFunds);
        commands.put(Commands.UNDO_LAST_COMMAND, this::undoLastCommand);
        commands.put(Commands.READ_NOTIFICATIONS, this::readNotifications);
        commands.put(Commands.CLEAR_NOTIFICATIONS, this::clearNotifications);
        commands.put(Commands.DISCARD_NOTIFICATIONS, this::discardNotifications);
        commands.put(Commands.SET_NEW_TRANSACTION_LIMIT_FOR_REGISTERED_CUSTOMER, this::setNewTransactionLimitForRegisteredCustomer);
        commands.put(Commands.SET_NEW_TRANSACTION_LIMIT_FOR_UNREGISTERED_CUSTOMER, this::setNewTransactionLimitForUnregisteredCustomer);
        commands.put(Commands.SET_NEW_DEBIT_ANNUAL_INTEREST_ON_BALANCE, this::setNewDebitAnnualInterestOnBalance);
        commands.put(Commands.SET_NEW_CREDIT_LIMIT_AMOUNT, this::setNewCreditLimitAmount);
        commands.put(Commands.SET_NEW_CREDIT_FEE, this::setNewCreditFee);
        commands.put(Commands.LIST_ALL_CUSTOMERS_ACCOUNTS, this::listAllCustomersAccounts);

        this.centralBank = centralBank;
        this.bank = bank;
        this.customer = customer;
        this.timeManager = TimeManager.getTimeManager(centralBank);
    }

    public void start() {
        String line;
        Scanner scanner = new Scanner(System.in);
        while (!(line = scanner.nextLine().toUpperCase(Locale.ROOT)).equals("")) {
            if (line.charAt(0) != '/' || line.length() == 1) {
                System.out.println("Incorrect input - not a command");
                continue;
            }

            if (line.split(" ")[0].equals("/EXIT")) {
                printColoredMessage("Shutdown...", GREEN);
                break;
            }

            Commands command;

            try {
                command = Commands.valueOf(line.substring(1).split(" ")[0]);
                String[] splitLine = line.split(" ");
                String[] args = Arrays.copyOfRange(splitLine, 1, splitLine.length);
                executeCommand(command, args);
            } catch (IllegalArgumentException e) {
                printColoredMessage("Incorrect input - not a command", RED);
            } catch (BanksException e) {
                printColoredMessage(e.getMessage(), RED);
            } catch (NullPointerException e) {
                printColoredMessage("The object to act is not chosen", RED);
            } catch (ArrayIndexOutOfBoundsException e){
                printColoredMessage("Insufficient amount of arguments", RED);
            }
        }
    }

    private void executeCommand(Commands command, String[] args) throws BanksException {
        commands.get(command).accept(args);
    }

    private void printColoredMessage(String message, String color) {
        System.out.println(color + message + RESET);
    }

    private void printInfoCurrentAccount(String[] args) {
        System.out.println(account.getId() + " " + account.getType() + " has " + account.getFunds());
    }

    private void chooseAccount(String[] args) {
        account = bank.getAccountById(args[0]);
        printColoredMessage("Chosen account to act with: " + account.getId(), GREEN);
    }

    private void addDebitAccount(String[] args) {
        var account = bank.createDebitAccount(customer);
        printColoredMessage("Debit account successfully created with id: " + account.getId(), GREEN);
    }

    private void addCreditAccount(String[] args) {
        var account = bank.createCreditAccount(customer);
        printColoredMessage("Credit account successfully created with id: " + account.getId(), GREEN);
    }

    private void addDepositAccount(String[] args) {
        var account = bank.createDepositAccount(customer, Integer.parseInt(args[0]), Double.parseDouble(args[1]));
        printColoredMessage("Deposit account successfully created with id: " + account.getId(), GREEN);
    }

    private void deleteAccountById(String[] args) {
        try {
            bank.removeAccount(bank.getAccountById(args[0]));
        } catch (BanksException e) {
            printColoredMessage(e.getMessage(), RED);
            return;
        }

        printColoredMessage("Account with id: " + args[0] + " was successfully deleted", GREEN);
    }

    private void transferFunds(String[] args) {
        try {
            lastExecutedCommand = bank.transferFundsBetweenAccounts(
                    account,
                    bank.getAccountById(args[0]),
                    Double.parseDouble(args[1]));
        } catch (BanksException e) {
            printColoredMessage(e.getMessage(), RED);
            return;
        }

        printColoredMessage("Funds were transferred from account with id" + account.getId() +
                " to account with id: " + args[0], GREEN);
    }

    private void withdrawFunds(String[] args) {
        try {
            lastExecutedCommand = bank.withdrawFundsFromAccount(
                    account,
                    Double.parseDouble(args[0]));
        } catch (BanksException e) {
            printColoredMessage(e.getMessage(), RED);
            return;
        }

        printColoredMessage("Funds were withdrawn from account with id: " + account.getId(), GREEN);
    }

    private void addFunds(String[] args) {
        try {
            lastExecutedCommand = bank.addFundsToAccount(
                    account,
                    Double.parseDouble(args[0]));
        } catch (BanksException e) {
            printColoredMessage(e.getMessage(), RED);
            return;
        }

        printColoredMessage("Funds were added to account with id: " + account.getId(), GREEN);
    }

    private void undoLastCommand(String[] args) {
        try {
            bank.undoCommand(lastExecutedCommand);
        } catch (BanksException e) {
            printColoredMessage(e.getMessage(), RED);
            return;
        }
        
        lastExecutedCommand = null;
        printColoredMessage("Last command was successfully undone", GREEN);
    }

    private void dissipateDays(String[] args) {
        timeManager.dissipateDays(Integer.parseInt(args[0]));
        printColoredMessage("Successfully rewound " + args[0] + " days", PURPLE);
    }

    private void dissipateMonths(String[] args) {
        timeManager.dissipateMonths(Integer.parseInt(args[0]));
        printColoredMessage("Successfully rewound " + args[0] + " months", PURPLE);
    }

    private void readNotifications(String[] args) {
        customer.getNotifications().forEach(System.out::println);
    }

    private void clearNotifications(String[] args) {
        customer.clearNotifications();
    }

    private void discardNotifications(String[] args) {
        customer.clearNotifications();
        printColoredMessage("Cleared all notifications", GREEN);
    }

    private void setNewCreditFee(String[] args) {
        centralBank.setNewCreditFee(Double.parseDouble(args[0]), bank);
        printColoredMessage("New credit fee " + args[0] + " was set", GREEN);
    }

    private void setNewCreditLimitAmount(String[] args) {
        centralBank.setNewCreditLimitAmount(Double.parseDouble(args[0]), bank);
        printColoredMessage("New credit limit " + args[0] + " was set", GREEN);
    }

    private void setNewDebitAnnualInterestOnBalance(String[] args) {
        centralBank.setNewDebitAnnualInterestOnBalance(Double.parseDouble(args[0]), bank);
        printColoredMessage("New debit annual interest " + args[0] + " was set", GREEN);
    }

    private void setNewTransactionLimitForUnregisteredCustomer(String[] args) {
        centralBank.setNewTransactionLimitForUnregisteredCustomer(Double.parseDouble(args[0]), bank);
        printColoredMessage("New transaction limit for unregistered customers " + args[0] + " was set", GREEN);
    }

    private void setNewTransactionLimitForRegisteredCustomer(String[] args) {
        centralBank.setNewTransactionLimitForRegisteredCustomer(Double.parseDouble(args[0]), bank);
        printColoredMessage("New transaction limit for registered customers " + args[0] + " was set", GREEN);
    }

    private void listAllCustomersAccounts(String[] args) {
        customer.getAccountIds().forEach(id ->
            {
                var account = bank.getAccountById(id.toString());
                System.out.println(account.getId() + " " + account.getType() + " has " + account.getFunds());
            });
    }
}
