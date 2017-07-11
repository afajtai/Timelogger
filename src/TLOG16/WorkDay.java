/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TLOG16;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.List;
import static javafx.util.Duration.hours;
import static javafx.util.Duration.minutes;

/**
 *
 * @author andri
 */
public class WorkDay {

    List<Task> task;
    long requiredMinPerDay = 450;
    LocalDate actualDay;
    long sumPerDay;

    public void setRequiredMinPerDay(long requiredMinPerDay) {
        this.requiredMinPerDay = requiredMinPerDay;
    }

    public void setActualDay(int year, int month, int day) {
        this.actualDay = LocalDate.of(year, month, day);
    }

    public List<Task> getTask() {
        return task;
    }

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
        sumPerDay=0;
        for (int i = 0; i < this.task.size(); i++) {
            if (this.task.get(i).startTime.isBefore(this.task.get(i).endTime)) {
               long taskMinutes = MINUTES.between(task.get(i).startTime, task.get(i).endTime);
                this.sumPerDay+=taskMinutes;
            }
        }
        return sumPerDay;
    }

    long getExtraMinPerDay() {
        return  this.getSumPerDay()-requiredMinPerDay ;
    }

    void addTask(Task taskToAdd) {
        if (Util.isSeparatedTime(taskToAdd, task) && Util.isMultipleQuarterHour(taskToAdd.getMinPerTask())) {
            task.add(taskToAdd);
        } else {
            //else part will be implemented later
        }

    }

}
