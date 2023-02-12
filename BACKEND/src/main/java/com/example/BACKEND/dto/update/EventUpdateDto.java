package com.example.BACKEND.dto.update;

public class EventUpdateDto {

    private Long id;
    private String name;
    private String startDate;
    private String endDate;

    public EventUpdateDto() {
    }

    public EventUpdateDto(Long id, String name, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
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
