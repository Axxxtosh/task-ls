package com.letsservice.calllog.data.model.response;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ashet on 03-08-2018.
 */

public class Call {

     private String name;
    private String number;
    private Date callDate;
    private String duration;
    private String type ;

    public Call(String name, String number, Date callDate, String duration, String type) {
        this.name = name;
        this.number = number;

        this.callDate = callDate;
        this.duration = duration;
        this.type = type;

    }

    private int dircode ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    public String getCallDate() {

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date today = callDate;
        String reportDate = df.format(today);

        return reportDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
