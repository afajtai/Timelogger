/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TLOG16;

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
}
