package com.example.BACKEND.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class StringDateValidator {

    Map<Integer,Integer> nrOfDaysPerMonth = new HashMap<>();

    public StringDateValidator(){
        nrOfDaysPerMonth.put(1,31);
        nrOfDaysPerMonth.put(2,28);
        nrOfDaysPerMonth.put(3,31);
        nrOfDaysPerMonth.put(4,30);
        nrOfDaysPerMonth.put(5,31);
        nrOfDaysPerMonth.put(6,30);
        nrOfDaysPerMonth.put(7,31);
        nrOfDaysPerMonth.put(8,31);
        nrOfDaysPerMonth.put(9,30);
        nrOfDaysPerMonth.put(10,31);
        nrOfDaysPerMonth.put(11,30);
        nrOfDaysPerMonth.put(12,31);
    }

    public Integer getNrOfDays(Integer month, Integer year){
        if(month == 2 && year % 4 == 0 ) {
            return 29;
        }
        else{
            return nrOfDaysPerMonth.get(month);
        }
    }
    public Boolean dateTimeIsValid(String data){
        try {
            String[] dataSplitted = data.split(" ");
            return dateIsValid(dataSplitted[0]) && timeIsValid(dataSplitted[1]);
        } catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
    }
    public Boolean dateIsValid(String data){
        String[] dataSplitted = data.split("-");
        Boolean hasRequiredElements =  dataSplitted.length == 3;
        if(hasRequiredElements){
            Integer year = Integer.parseInt(dataSplitted[0]);
            Integer month = Integer.parseInt(dataSplitted[1]);
            Integer day = Integer.parseInt(dataSplitted[2]);
            return 0<=day && day<= getNrOfDays(month,year) &&
                    dataSplitted[0].length()==4 &&
                    dataSplitted[1].length()==2 &&
                    dataSplitted[2].length()==2;
        }
        else{
            return false;
        }
    }

    public Boolean timeIsValid(String time){
        String[] timeSpitted = time.split(":");
        Boolean hasRequiredElements =  timeSpitted.length == 2;
        if(hasRequiredElements) {
            Boolean validHour = 0 <= Integer.parseInt(timeSpitted[0]) && Integer.parseInt(timeSpitted[0]) <= 23;
            Boolean validMinute = 0 <= Integer.parseInt(timeSpitted[1]) && Integer.parseInt(timeSpitted[1]) <= 59;
            return validHour && validMinute && timeSpitted[0].length()==2 && timeSpitted[1].length()==2;
        }
        else{
            return false;
        }
    }


}
