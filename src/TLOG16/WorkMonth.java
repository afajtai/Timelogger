/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TLOG16;

import java.time.YearMonth;
import java.util.List;

/**
 *
 * @author andri
 */
public class WorkMonth {

    List<WorkDay> days;
    YearMonth date;
    long sumPerMonth;
    long requiredMinPerMonth;

    public WorkMonth(List<WorkDay> days, int year, int month, long sumPerMonth, long requiredMinPerMonth) {
        this.days = days;
        date = YearMonth.of(year, month);
        this.sumPerMonth = sumPerMonth;
        this.requiredMinPerMonth = requiredMinPerMonth;

    }

    public List<WorkDay> getDays() {
        return days;
    }

    public YearMonth getDate() {
        return date;
    }

    public long getSumPerMonth() {
        return sumPerMonth;
    }

    public long getRequiredMinPerMonth() {
        return requiredMinPerMonth;
    }

    long getExtraMinPerMonth() {
        long sumOfMinutes = 0;
        for (int i = 0; i < days.size(); i++) {
            sumOfMinutes += this.days.get(i).getExtraMinPerDay();
        }
        return sumOfMinutes;
    }

    boolean isNewDate(WorkDay maybeNewDate) {
        boolean isNotNewDate = false;
        if (days.contains(maybeNewDate) == true) {
            isNotNewDate = true;
        }
        return !isNotNewDate;
    }

    boolean isSameMonth(WorkDay newDay) {
        boolean isIt = false;
        if (newDay.actualDay.getMonth() == date.getMonth()) {
            isIt = true;
        }
        return isIt;
    }

    void addWorkDay(WorkDay workDayToAdd, boolean isWeekendEnabled) {
        if (isSameMonth(workDayToAdd) && isNewDate(workDayToAdd)) {
            if (isWeekendEnabled) {
                days.add(workDayToAdd);
            } else if (Util.isWeekDay(workDayToAdd.actualDay) && isWeekendEnabled == false) {
                days.add(workDayToAdd);
            }
        }

    }

    void addWorkDay(WorkDay workDayToAdd) {
        if (isSameMonth(workDayToAdd) && isNewDate(workDayToAdd)) {
            if (Util.isWeekDay(workDayToAdd.actualDay)) {
                days.add(workDayToAdd);
            }
        }
    }
}
