package com.itmo.models;

import com.itmo.interfaces.AbstractCentralBank;
import com.itmo.tools.Config;

public class TimeManager {
    private static TimeManager timeManager;
    private final AbstractCentralBank centralBank;
    private int daysPassed;

    private TimeManager(AbstractCentralBank centralBank) {
        this.centralBank = centralBank;
    }

    public static TimeManager getTimeManager(AbstractCentralBank centralBank) {
        if (timeManager == null) {
            timeManager = new TimeManager(centralBank);
        }

        return timeManager;
    }

    public void dissipateDays(int numberOfDays) {
        for (int i = 0; i < numberOfDays; i++) {
            daysPassed++;
            centralBank.notifyBanksDayPassed();
            if (daysPassed % Config.DAYS_IN_MONTH == 0) {
                centralBank.notifyBanksMonthPassed();
            }
        }
    }

    public void dissipateMonths(int numberOfMonths) {
        dissipateDays(numberOfMonths * Config.DAYS_IN_MONTH);
    }
}
