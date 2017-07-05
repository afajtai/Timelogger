/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TLOG16;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author andri
 */
public class WorkDay {

    List<Task> task;
    long requiredMinPerDay = 450;
    LocalDate actualDay;
    long sumPerDay;

    public WorkDay(List<Task> task, long requiredMinPerDay, int year, int month, int day, long sumPerDay) {
        this.task = task;
        this.sumPerDay = sumPerDay;
        this.requiredMinPerDay = requiredMinPerDay;
        this.actualDay = LocalDate.of(year, month, day);
    }

    public WorkDay(List<Task> task, int year, int month, int day, long sumPerDay) {
        this.task = task;
        this.sumPerDay = sumPerDay;
        this.requiredMinPerDay = 450;
        this.actualDay = LocalDate.of(year, month, day);
    }

    public WorkDay(List<Task> task, long requiredMinPerDay, long sumPerDay) {
        this.task = task;
        this.sumPerDay = sumPerDay;
        this.requiredMinPerDay = requiredMinPerDay;
        this.actualDay = LocalDate.now();
    }

    public WorkDay(List<Task> task, long sumPerDay) {
        this.task = task;
        this.sumPerDay = sumPerDay;
        this.requiredMinPerDay = 450;
        this.actualDay = LocalDate.now();
    }

    public long getRequiredMinPerDay() {
        return requiredMinPerDay;
    }

    public LocalDate getActualDay() {
        return actualDay;
    }

    public long getSumPerDay() {
        return sumPerDay;
    }

    long getExtraMinPerDay() {
        return requiredMinPerDay - sumPerDay;
    }

    boolean isSeparatedTime(Task t) {
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
    
    void addTask(Task taskToAdd){
      if(isSeparatedTime(taskToAdd) && taskToAdd.isMultipleQuarterHour()){
          task.add(taskToAdd);
      } else {
          //else part will be implemented later
      }
        
    }
    
    boolean isWeekDay(){
        boolean isWeekend=false;
        if ((actualDay.getDayOfWeek() == DayOfWeek.SATURDAY) && (actualDay.getDayOfWeek() == DayOfWeek.SUNDAY)){
           isWeekend = true;
        }
        return !isWeekend;
    }
}
