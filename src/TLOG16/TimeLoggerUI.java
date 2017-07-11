/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TLOG16;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author andri
 */
public class TimeLoggerUI {

    static List<String> menuItems = Arrays.asList(
            "Exit",
            "List months",
            "List days",
            "List tasks for a specific day",
            "Add new month",
            "Add day to a specific month",
            "Start a task for a day",
            "Finish a specific task",
            "Delete a task",
            "Modify a task",
            "Statistics");

    public static void printMainMenu() {
        for (int i = 0; i < menuItems.size(); i++) {
            System.out.println(i + ". " + menuItems.get(i));
        }
    }

    public static void doMainMenu() {
        printMainMenu();
        mainMenuResponseHandler(readResponse());
    }

    public static String readResponse() {
        String userResponse;
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        userResponse = reader.nextLine(); // Scans the next token of the input as an int.
        if (userResponse.isEmpty()) {
            userResponse = "";
        }
        clearScreen();
        return userResponse;
    }

    public static boolean isThisStringAnInteger(String string) {
        boolean doTheNumberOfDigitsAndCharactersEqual = false;
        if (Util.digitCounter(string) == string.length()) {
            doTheNumberOfDigitsAndCharactersEqual = true;
        }
        return doTheNumberOfDigitsAndCharactersEqual;
    }

    public static int parseStringResponse(String responseInString) {
        return Integer.parseInt(responseInString);
    }

    public static boolean isThisAValidMenuOption(int menuOptionNumber) {
        boolean isThisValid = false;
        if (menuOptionNumber >= 0 && menuOptionNumber < menuItems.size()) {
            isThisValid = true;
        }
        return isThisValid;
    }

    public static void invalidMainMenuInput() {
        writeInvalid();
        mainMenuResponseHandler(readResponse());
    }

    public static void writeInvalid() {
        System.out.println("Invalid imput");
    }

    public static void mainMenuResponseHandler(String response) {
        if (isThisStringAnInteger(response)) {
            int responseInInteger = parseStringResponse(response);
            if (isThisAValidMenuOption(responseInInteger)) {
                menuOpener(responseInInteger);
            } else {
                invalidMainMenuInput();
            }
        } else {
            invalidMainMenuInput();
        }
    }

    public static void menuOpener(int menuNumber) {
        switch (menuNumber) {
            case 0:
                doExit();
                break;
            case 1:
                doListMonth();
                break;
            case 2:
                doListDays();
                break;
            case 3:
                doListTasks();
                break;
            case 4:
                doAddNewMonth();
                break;
            case 5:
                doAddNewDay();
                break;
            case 6:
                doStartTask();
                break;
            case 7:
                doFinishTask();
                break;
            case 8:
                doDeleteTask();
                break;
            case 9:
                doModifyTask();
                break;
            case 10:
                doStatistics();
                break;
        }
        doMainMenu();
    }

    public static void doExit() {
        System.exit(0);
    }

    public static void doListMonth() {
        for (int i = 0; i < TimeLogger.months.size(); i++) {
            System.out.println(i + 1 + ". " + TimeLogger.months.get(i).date);
        }
    }

    public static List<String> readNewMonth() {
        List<String> newDate = new ArrayList<>();
        System.out.println("year:");
        newDate.add(readResponse());
        System.out.println("month:");
        newDate.add(readResponse());
        return newDate;
    }

    public static boolean isItAValidNewMonth(String year, String month) {
        boolean isItValid = false;
        if (isThisStringAnInteger(year) && isThisStringAnInteger(month)) {
            int yearInInteger = parseStringResponse(year);
            int monthInInteger = parseStringResponse(month);
            if (Util.digitCounter(year) == 4
                    && monthInInteger > 0
                    && monthInInteger < 13
                    && TimeLogger.isNewMonth(new WorkMonth(yearInInteger, monthInInteger))) {
                isItValid = true;
            }
        }
        return isItValid;
    }

    public static void addNewMonth(int year, int month) {
        List<WorkDay> days = new ArrayList<>();
        TimeLogger.months.add(new WorkMonth(days, year, month, 0, 0));
    }

    public static void doAddNewMonth() {
        List<String> date = readNewMonth();
        if (isItAValidNewMonth(date.get(0), date.get(1))) {
            addNewMonth(parseStringResponse(date.get(0)), parseStringResponse(date.get(1)));
            System.out.println("Month added successfully!");
        } else {
            writeInvalid();
            doAddNewMonth();
        }
    }

    public static boolean isItAValidMonthIndex(String monthIndex) {
        boolean isItValid = false;

        if (isThisStringAnInteger(monthIndex)) {
            int monthIndexInt = parseStringResponse(monthIndex) - 1;
            if (monthIndexInt < TimeLogger.months.size() && monthIndexInt >= 0) {
                isItValid = true;
            }
        }

        return isItValid;

    }

    public static WorkDay askForDay(int year, int month) {
        WorkDay dayToReturn = null;
        System.out.println("Which day is it?");
        String dayName = readResponse();
        System.out.println("Required working hours?");
        String requiredWorkingHours = readResponse();
        List<Task> tasks = new ArrayList<>();
        if (isThisStringAnInteger(dayName) && isThisStringAnInteger(requiredWorkingHours)) {
            int day = parseStringResponse(dayName);
            dayToReturn = new WorkDay(tasks, year, month, day, 0);
            dayToReturn.setRequiredMinPerDay((parseStringResponse(requiredWorkingHours) * 60));
        } else {
        }

        return dayToReturn;
    }

    public static boolean isItAValidDay(WorkDay workDayToCheck) {
        boolean isItValid = false;
        if (workDayToCheck != null && workDayToCheck.getActualDay().getDayOfMonth() < 32 && workDayToCheck.requiredMinPerDay <= 1440) {
            isItValid = true;
        }
        return isItValid;
    }

    public static void clearScreen() {
        for (int i = 0; i < 30; i++) {
            System.out.println("");
        }

    }

    public static void addDay(int monthIndex, WorkDay workDayToBeAdded) {
        TimeLogger.months.get(monthIndex).addWorkDay(workDayToBeAdded);
        System.out.println("Day is succesfully added!");
    }

    public static void doAddNewDay() {
        doListMonth();
        String monthIndex = readResponse();
        if (isItAValidMonthIndex(monthIndex)) {
            int monthIndexInteger = parseStringResponse(monthIndex) - 1;
            int year = TimeLogger.months.get(monthIndexInteger).date.getYear();
            int month = TimeLogger.months.get(monthIndexInteger).date.getMonthValue();
            try {
                WorkDay newDay = askForDay(year, month);
                if (isItAValidDay(newDay)) {
                    addDay(parseStringResponse(monthIndex) - 1, newDay);
                } else {
                    writeInvalid();
                    doAddNewDay();
                }
            } catch (java.time.DateTimeException e) {
                writeInvalid();
                doAddNewDay();
            }

        }
    }

    public static void listDays(int monthIndex) {
        for (int i = 0; i < TimeLogger.months.get(monthIndex).days.size(); i++) {
            System.out.println(i + 1 + ". " + TimeLogger.months.get(monthIndex).days.get(i).actualDay);
        }
    }

    public static void doListDays() {
        doListMonth();
        String monthIndex = readResponse();
        if (isItAValidMonthIndex(monthIndex)) {
            listDays(parseStringResponse(monthIndex) - 1);
        }
    }

    public static boolean isItAValidDayIndex(String dayIndex, int validMonthIndex) {
        boolean isItValid = false;

        if (isThisStringAnInteger(dayIndex)) {
            int dayIndexInt = parseStringResponse(dayIndex) - 1;
            if (dayIndexInt < TimeLogger.months.get(validMonthIndex).days.size() && dayIndexInt >= 0) {
                isItValid = true;
            }
        }

        return isItValid;
    }

    public static Task askForTask(int monthIndex, int dayIndex) {
        System.out.println("TaskId:");
        String taskId = readResponse();
        System.out.println("Comment:");
        String comment = readResponse();
        System.out.println("Press enter to use the earlier end time:(" + earlierEndTime(monthIndex, dayIndex) + ")");
        System.out.println("Or type in another time.");
        String startTime = readResponse();
        if (startTime.isEmpty()) {
            startTime = earlierEndTime(monthIndex, dayIndex);
            Task taskToAdd = new Task(taskId, comment, startTime, "00:00");
            return taskToAdd;
        } else if (isItAValidStartTime(startTime)) {
            Task taskToAdd = new Task(taskId, comment, startTime, "00:00");
            return taskToAdd;
        } else {
            Task emptyTask = null;
            return emptyTask;
        }

    }

    public static String earlierEndTime(int monthIndex, int dayIndex) {
        int lastIndex = (TimeLogger.months.get(monthIndex).days.get(dayIndex).task.size()) - 1;
        String earlierTime;
        if (lastIndex >= 0) {
            earlierTime = TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(lastIndex).endTime.toString();
        } else {
            earlierTime = "00:00";
        }
        return earlierTime;

    }

    public static void addTask(int monthIndex, int dayIndex, Task taskToBeAdded) {

        TimeLogger.months.get(monthIndex).days.get(dayIndex).task.add(taskToBeAdded);
        System.out.println("Task is added succesfully!");

    }

    public static boolean isNewTaskId(int monthIndex, int dayIndex, String taskId) {
        boolean isNotNewTaskId = false;
        for (int i = 0; i < TimeLogger.months.get(monthIndex).days.get(dayIndex).task.size(); i++) {
            if (taskId.equals(TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(i).taskId)) {
                isNotNewTaskId = true;
            }
        }
        return !isNotNewTaskId;
    }

    public static void doStartTask() {
        try {
            doListMonth();
            String monthIndex = readResponse();
            if (isItAValidMonthIndex(monthIndex)) {
                int monthIndexInt = parseStringResponse(monthIndex) - 1;
                listDays(monthIndexInt);
                String dayIndex = readResponse();
                if (isItAValidDayIndex(dayIndex, monthIndexInt)) {
                    int dayIndexInt = parseStringResponse(dayIndex) - 1;
                    Task taskToAdd = askForTask(monthIndexInt, dayIndexInt);
                    if ((taskToAdd.isValidLTTaskId() || taskToAdd.isValidTaskId()) && isNewTaskId(monthIndexInt, dayIndexInt, taskToAdd.taskId)) {
                        addTask(monthIndexInt, dayIndexInt, taskToAdd);
                    } else {
                        writeInvalid();
                        doStartTask();
                    }

                }
            }
        } catch (java.lang.NullPointerException e) {
            writeInvalid();
            doStartTask();
        }
    }

    public static boolean isItAValidStartTime(String startTimeToCheck) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(startTimeToCheck.trim());
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static void listTasks(int monthIndex, int dayIndex) {
        for (int i = 0; i < TimeLogger.months.get(monthIndex).days.get(dayIndex).task.size(); i++) {
            System.out.println(i + 1 + ". "
                    + TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(i).taskId + " "
                    + TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(i).comment + " "
                    + TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(i).startTime + " "
                    + TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(i).endTime);
        }
    }

    public static void listUnfinishedTasks(int monthIndex, int dayIndex) {
        for (int i = 0; i < TimeLogger.months.get(monthIndex).days.get(dayIndex).task.size(); i++) {
            if (TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(i).endTime == LocalTime.of(00, 00)) {
                System.out.println(i + 1 + ". "
                        + TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(i).taskId + " "
                        + TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(i).comment + " "
                        + TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(i).startTime + " "
                        + TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(i).endTime);
            }

        }
    }

    public static void doListTasks() {
        doListMonth();
        String monthIndex = readResponse();
        if (isItAValidMonthIndex(monthIndex)) {
            int monthIndexInt = parseStringResponse(monthIndex) - 1;
            listDays(monthIndexInt);
            String dayIndex = readResponse();
            if (isItAValidDayIndex(dayIndex, monthIndexInt)) {
                int dayIndexInt = parseStringResponse(dayIndex) - 1;
                listTasks(monthIndexInt, dayIndexInt);
            }
        }
    }

    public static void addEndTimeToTask(int monthIndex, int dayIndex, int taskIndex, String endTime) {
        if (isItAValidStartTime(endTime)) {
            TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(taskIndex).setEndTime(endTime);
        } else {
            writeInvalid();
            doFinishTask();
        }
    }

    public static String askForEndTime() {
        String timeToBeReturned = readResponse();
        return timeToBeReturned;
    }

    public static boolean isItAValidTaskIndex(int monthIndex, int dayIndex, String taskIndex) {
        boolean isItValid = false;

        if (isThisStringAnInteger(taskIndex)) {
            int taskIndexInt = parseStringResponse(taskIndex) - 1;
            if (taskIndexInt < TimeLogger.months.get(monthIndex).days.get(dayIndex).task.size() && taskIndexInt >= 0) {
                isItValid = true;
            }
        }

        return isItValid;
    }

    public static void doFinishTask() {
        doListMonth();
        String monthIndex = readResponse();
        if (isItAValidMonthIndex(monthIndex)) {
            int monthIndexInt = parseStringResponse(monthIndex) - 1;
            listDays(monthIndexInt);
            String dayIndex = readResponse();
            if (isItAValidDayIndex(dayIndex, monthIndexInt)) {
                int dayIndexInt = parseStringResponse(dayIndex) - 1;
                listUnfinishedTasks(monthIndexInt, dayIndexInt);
                String taskIndex = readResponse();
                if (isItAValidTaskIndex(monthIndexInt, dayIndexInt, taskIndex)) {
                    int taskIndexInt = parseStringResponse(taskIndex) - 1;
                    System.out.println("Type in the end time (HH:mm)");
                    String endTime = askForEndTime();
                    addEndTimeToTask(monthIndexInt, dayIndexInt, taskIndexInt, endTime);
                    System.out.println("Succesfully finished!");
                } else {
                    writeInvalid();
                    doFinishTask();
                }
            } else {
                writeInvalid();
                doFinishTask();
            }
        } else {
            writeInvalid();
            doFinishTask();
        }
    }

    public static boolean areYouSure() {
        System.out.println("Are you sure? y/n");
        String sureness = readResponse();
        if (sureness.equals("y")) {
            return true;
        } else {
            return false;
        }
    }

    public static void deleteTask(int monthIndex, int dayIndex, int taskIndex) {
        TimeLogger.months.get(monthIndex).days.get(dayIndex).task.remove(taskIndex);
        System.out.println("The task has been deleted succesfully");
    }

    public static void doDeleteTask() {
        doListMonth();
        String monthIndex = readResponse();
        if (isItAValidMonthIndex(monthIndex)) {
            int monthIndexInt = parseStringResponse(monthIndex) - 1;
            listDays(monthIndexInt);
            String dayIndex = readResponse();
            if (isItAValidDayIndex(dayIndex, monthIndexInt)) {
                int dayIndexInt = parseStringResponse(dayIndex) - 1;
                listTasks(monthIndexInt, dayIndexInt);
                System.out.println("Choose a task to delete:");
                String taskIndex = readResponse();
                if (isItAValidTaskIndex(monthIndexInt, dayIndexInt, taskIndex)) {
                    int taskIndexInt = parseStringResponse(taskIndex) - 1;
                    if (areYouSure()) {
                        deleteTask(monthIndexInt, dayIndexInt, taskIndexInt);

                    } else {
                        doMainMenu();
                    }
                } else {
                    doDeleteTask();
                }
            } else {
                doDeleteTask();
            }
        } else {
            doDeleteTask();
        }
    }

    public static void listTaskFields() {
        System.out.println("Choose a field:" + "\n"
                + "1. taskId"
                + "\n" + "2. comment"
                + "\n" + "3. startTime"
                + "\n" + "4. endTime");
    }

    public static String askForTaskFieldIndex() {
        String taskFieldIndex = readResponse();
        return taskFieldIndex;
    }

    public static boolean isItAValidTaskFieldIndex(String taskFieldIndex) {
        boolean isItValid = false;
        if (isThisStringAnInteger(taskFieldIndex)) {
            int taskFieldIndexInt = parseStringResponse(taskFieldIndex);
            if (taskFieldIndexInt > 0 && taskFieldIndexInt < 5) {
                isItValid = true;
            }
        }
        return isItValid;
    }

    public static void taskFieldModificationHandler(int taskFieldIndex, int monthIndex, int dayIndex, int taskIndex) {
        switch (taskFieldIndex) {
            case 1:
                modifyTaskId(monthIndex, dayIndex, taskIndex);
                break;
            case 2:
                modifyComment(monthIndex, dayIndex, taskIndex);
                break;
            case 3:
                modifyStartTime(monthIndex, dayIndex, taskIndex);
                break;
            case 4:
                modifyEndTime(monthIndex, dayIndex, taskIndex);
                break;
            default:
                doModifyTask();
                break;
        }
    }

    public static String previousTaskId(int monthIndex, int dayIndex, int taskIndex) {
        return TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(taskIndex).taskId;
    }

    public static String previousComment(int monthIndex, int dayIndex, int taskIndex) {
        return TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(taskIndex).comment;
    }

    public static String previousStartTime(int monthIndex, int dayIndex, int taskIndex) {
        return TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(taskIndex).startTime.toString();
    }

    public static String previousEndTime(int monthIndex, int dayIndex, int taskIndex) {
        return TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(taskIndex).endTime.toString();
    }

    public static void modifyTaskId(int monthIndex, int dayIndex, int taskIndex) {
        String previousTaskId = previousTaskId(monthIndex, dayIndex, taskIndex);
        System.out.println("Type in the new taskId or press enter leave the existing one (" + previousTaskId + ").");
        String taskId = readResponse();
        if (!taskId.isEmpty()) {
            Task taskToTestTaskId = new Task(taskId, "", "00:00", "00:00");
            if (taskToTestTaskId.isValidTaskId()) {
                TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(taskIndex).setTaskId(taskId);
                System.out.println("The task has been modified succesfully!");
            } else {
                writeInvalid();
                modifyTaskId(monthIndex, dayIndex, taskIndex);
            }

        } else {
            System.out.println("The task will remain the same.");
        }

    }

    public static void modifyComment(int monthIndex, int dayIndex, int taskIndex) {
        String previousComment = previousComment(monthIndex, dayIndex, taskIndex);
        System.out.println("Type in the new comment or press enter leave the existing one (" + previousComment + ").");
        String comment = readResponse();
        if (!comment.isEmpty()) {

            TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(taskIndex).setComment(comment);
            System.out.println("The task has been modified succesfully!");

        } else {
            System.out.println("The task will remain the same.");
        }
    }

    public static void modifyStartTime(int monthIndex, int dayIndex, int taskIndex) {
        String previousStartTime = previousStartTime(monthIndex, dayIndex, taskIndex);
        System.out.println("Type in the new startTime or press enter leave the existing one (" + previousStartTime + ").");
        String startTime = readResponse();
        if (!startTime.isEmpty()) {
            if (isItAValidStartTime(startTime)) {
                TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(taskIndex).setStartTime(startTime);
                System.out.println("The task has been modified succesfully!");
            } else {
                writeInvalid();
                modifyStartTime(monthIndex, dayIndex, taskIndex);
            }

        } else {
            System.out.println("The task will remain the same.");
        }
    }

    public static void modifyEndTime(int monthIndex, int dayIndex, int taskIndex) {
        String previousEndTime = previousEndTime(monthIndex, dayIndex, taskIndex);
        System.out.println("Type in the new endTime or press enter leave the existing one (" + previousEndTime + ").");
        String endTime = readResponse();
        if (!endTime.isEmpty()) {
            if (isItAValidStartTime(endTime)) {
                TimeLogger.months.get(monthIndex).days.get(dayIndex).task.get(taskIndex).setEndTime(endTime);
                System.out.println("The task has been modified succesfully!");
            } else {
                writeInvalid();
                modifyEndTime(monthIndex, dayIndex, taskIndex);
            }

        } else {
            System.out.println("The task will remain the same.");
        }
    }

    public static void doModifyTask() {
        doListMonth();
        String monthIndex = readResponse();
        if (isItAValidMonthIndex(monthIndex)) {
            int monthIndexInt = parseStringResponse(monthIndex) - 1;
            listDays(monthIndexInt);
            String dayIndex = readResponse();
            if (isItAValidDayIndex(dayIndex, monthIndexInt)) {
                int dayIndexInt = parseStringResponse(dayIndex) - 1;
                listTasks(monthIndexInt, dayIndexInt);
                System.out.println("Choose a task:");
                String taskIndex = readResponse();
                if (isItAValidTaskIndex(monthIndexInt, dayIndexInt, taskIndex)) {
                    int taskIndexInt = parseStringResponse(taskIndex) - 1;
                    listTaskFields();
                    String taskFieldIndex = askForTaskFieldIndex();
                    if (isItAValidTaskFieldIndex(taskFieldIndex)) {
                        int taskFieldIndexInt = parseStringResponse(taskFieldIndex);
                        taskFieldModificationHandler(taskFieldIndexInt, monthIndexInt, dayIndexInt, taskIndexInt);
                    }

                }
            }
        }
    }
    
    public static void printMonthStatistics(int monthIndex){
        System.out.println(TimeLogger.months.get(monthIndex).date.getYear()+ " "+ TimeLogger.months.get(monthIndex).date.getMonth());
        System.out.println("==================");
        System.out.println("Required minutes: "+ TimeLogger.months.get(monthIndex).getRequiredMinPerMonth());
        System.out.println("Extra minutes: "+TimeLogger.months.get(monthIndex).getExtraMinPerMonth());  
        System.out.println("");
    }
    
    public static void printDaysStatistics(int monthIndex){
        for(int i=0;i<TimeLogger.months.get(monthIndex).days.size();i++){
            System.out.println(TimeLogger.months.get(monthIndex).days.get(i).actualDay);
            System.out.println("------------------");
            System.out.println("Required minutes:"+TimeLogger.months.get(monthIndex).days.get(i).requiredMinPerDay);
            System.out.println("Working minutes :"+TimeLogger.months.get(monthIndex).days.get(i).getSumPerDay());
            System.out.println("Extra minutes   :"+TimeLogger.months.get(monthIndex).days.get(i).getExtraMinPerDay());
            System.out.println("");
        }
    }
    public static void doStatistics(){
        doListMonth();
        System.out.println("Choose a month: ");
        String monthIndex = readResponse();
        if (isItAValidMonthIndex(monthIndex)) {
            int monthIndexInt = parseStringResponse(monthIndex)-1;
            printMonthStatistics(monthIndexInt);
            printDaysStatistics(monthIndexInt);
        } else{
            writeInvalid();
            doStatistics();
        }
        
    }

}
