/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TLOG16;

import java.util.List;

/**
 *
 * @author andri
 */
public class TimeLogger {
    List <WorkMonth> months;

    public List<WorkMonth> getMonths() {
        return months;
    }
    
    boolean isNewMonth(WorkMonth monthToBeChecked){
        boolean isItNewMonth = false;
        if(!months.contains(monthToBeChecked)){
            isItNewMonth=true;
        }
        return isItNewMonth;
    }
    
    void addMonth(WorkMonth monthToBeAdded){
        if(isNewMonth(monthToBeAdded)){
            months.add(monthToBeAdded);
        }
    }
}
