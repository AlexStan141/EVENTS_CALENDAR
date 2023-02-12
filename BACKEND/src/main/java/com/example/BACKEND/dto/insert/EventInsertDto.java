package com.example.BACKEND.dto.insert;

import java.io.Serializable;

public class EventInsertDto implements Serializable {

    private String name;
    private String startDate;
    private String endDate;

    public EventInsertDto() {
    }

    public EventInsertDto(String name, String startDate, String endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
