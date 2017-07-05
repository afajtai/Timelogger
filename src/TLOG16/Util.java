/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TLOG16;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author andri
 */
public class Util {
    
    public static boolean isMultipleQuarterHour(long time) {
        boolean isMultiple = false;
        if (time % 15 == 0) {
            isMultiple = true;
        }
        return isMultiple;
    }
    
    public static long roundToMultipleQuarterHour(LocalTime startTime, LocalTime endTime){
        long endTimeMinutes = endTime.getMinute() + endTime.getHour() * 60;
        long startTimeMinutes = startTime.getMinute() + startTime.getHour() * 60;
        
        return 15*(Math.round((endTimeMinutes - startTimeMinutes)/15));
    }
    
    public static boolean isSeparatedTime(Task t, List<Task> task) {
        boolean isNotSeparated = false;
        for (int i = 0; i <= task.size(); i++) {
            if ((task.get(i).startTime.compareTo(t.endTime) > 0) || (task.get(i).endTime.compareTo(t.startTime) < 0)) {
                //separated
            } else if (t != task.get(i)) {
                isNotSeparated = true;
            }
        }
        return !isNotSeparated;
    }
     public static boolean isWeekDay(LocalDate actualDay) {
        boolean isWeekend = false;
        if ((actualDay.getDayOfWeek() == DayOfWeek.SATURDAY) && (actualDay.getDayOfWeek() == DayOfWeek.SUNDAY)) {
            isWeekend = true;
        }
        return !isWeekend;
    }
}
