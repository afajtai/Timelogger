/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TLOG16;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andri
 */
public class TimeLogger {

    public static List<WorkMonth> months = new ArrayList<>();

    public List<WorkMonth> getMonths() {
        return months;
    }

    public static boolean isNewMonth(WorkMonth monthToBeChecked) {
        boolean isItNewMonth = true;
        for (int i = 0; i < months.size(); i++) {
            if (months.get(i).date.equals(monthToBeChecked.date)) {
                isItNewMonth = false;
            }
        }
        return isItNewMonth;
    }

    void addMonth(WorkMonth monthToBeAdded) {
        if (isNewMonth(monthToBeAdded)) {
            months.add(monthToBeAdded);
        }
    }
}
