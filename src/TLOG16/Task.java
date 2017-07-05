/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TLOG16;

import java.time.LocalTime;

/**
 *
 * @author andri
 */
public class Task {

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setStartTime(int hour, int min) {
        this.startTime = LocalTime.of(hour, min);
    }

    public void setStartTime(String timeInString) {
        int hour = Integer.parseInt(timeInString.substring(0, 2));
        int minute = Integer.parseInt(timeInString.substring(3));
        this.startTime = LocalTime.of(hour, minute);
    }

    public void setEndTime(int hour, int min) {
        this.endTime = LocalTime.of(hour, min);
    }

    public void setEndTime(String timeInString) {
        int hour = Integer.parseInt(timeInString.substring(0, 2));
        int minute = Integer.parseInt(timeInString.substring(3));
        this.endTime = LocalTime.of(hour, minute);
    }

    public String getTaskId() {
        return taskId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getComment() {
        return comment;
    }

    String taskId;
    LocalTime startTime;
    LocalTime endTime;
    String comment;

    public Task(String TaskId, String Comment, int StartHour, int StartMin, int EndHour, int EndMin) {
        this.taskId = TaskId;
        this.comment = Comment;
        startTime = LocalTime.of(StartHour, StartMin);
        endTime = LocalTime.of(EndHour, EndMin);

    }

    public Task(String taskId, String comment, String startTime, String endTime) {
        this.taskId = taskId;
        this.comment = comment;

        int hour = Integer.parseInt(startTime.substring(0, 2));
        int minute = Integer.parseInt(startTime.substring(3));
        this.startTime = LocalTime.of(hour, minute);

        hour = Integer.parseInt(endTime.substring(0, 2));
        minute = Integer.parseInt(endTime.substring(3));
        this.endTime = LocalTime.of(hour, minute);
    }

    public Task(String taskId) {
        this.taskId = taskId;
    }

    long getMinPerTask() {
        long endTimeMinutes = endTime.getMinute() + endTime.getHour() * 60;
        long startTimeMinutes = startTime.getMinute() + startTime.getHour() * 60;
        return endTimeMinutes - startTimeMinutes;
    }

    boolean isValidRedmineTaskId() {
        int digitCounter = 0;
        for (char c : taskId.toCharArray()) {
            if (c >= '0' && c <= '9') {
                ++digitCounter;
            }
        }
        boolean isValid = false;
        if (((digitCounter == 4) && (taskId.length() == 4)) || ((digitCounter == 7) && (taskId.startsWith("LT-")) && (taskId.length() == 7))) {
            isValid = true;
        }
        return isValid;
    }

    boolean isValidLTTaskId() {
        int digitCounter = 0;
        for (char c : taskId.toCharArray()) {
            if (c >= '0' && c <= '9') {
                ++digitCounter;
            }
        }
        boolean isValid = false;
        if ((digitCounter == 7) && (taskId.startsWith("LT-")) && (taskId.length() == 7)) {
            isValid = true;
        }
        return isValid;
    }

    boolean isValidTaskId() {
        boolean isItValidTaskId = false;
        if (isValidRedmineTaskId() || isValidLTTaskId()) {
            isItValidTaskId = true;
        }
        return isItValidTaskId;
    }

}
