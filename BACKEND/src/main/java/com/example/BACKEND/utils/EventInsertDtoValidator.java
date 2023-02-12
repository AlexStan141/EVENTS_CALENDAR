package com.example.BACKEND.utils;

import com.example.BACKEND.dto.insert.EventInsertDto;
import com.example.BACKEND.model.Event;
import com.example.BACKEND.repository.EventRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class EventInsertDtoValidator {

    private StringDateValidator stringDateValidator;
    private EventRepository eventRepository;

    public EventInsertDtoValidator(StringDateValidator stringDateValidator, EventRepository eventRepository) {
        this.stringDateValidator = stringDateValidator;
        this.eventRepository = eventRepository;
    }

    public List<String> isValid(EventInsertDto eventInsertDto) {
        List<String> l = new ArrayList<>();
        if (Objects.equals(eventInsertDto.getName(), "")) {
            l.add("Event must have a name!");
        }
        if(eventRepository.getNames().contains(eventInsertDto.getName())){
            l.add("The name is not unique!");
        }
        if (!stringDateValidator.dateTimeIsValid(eventInsertDto.getStartDate())) {
            l.add("Invalid start date format.Use format yyyy-MM-dd HH:mm");
        }
        if (!stringDateValidator.dateTimeIsValid(eventInsertDto.getEndDate())) {
            l.add("Invalid end date format.Use format yyyy-MM-dd HH:mm");
        }
        if(!l.contains("Invalid start date format.Use format yyyy-MM-dd HH:mm") &&
           !l.contains("Invalid end date format.Use format yyyy-MM-dd HH:mm")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDate = LocalDateTime.parse(eventInsertDto.getStartDate(), formatter);
            LocalDateTime endDate = LocalDateTime.parse(eventInsertDto.getEndDate(), formatter);
            LocalDateTime nowDate = LocalDateTime.now();
            if (startDate.isBefore(nowDate)) {
                l.add("You cannot plan events in the past!");
            }
            if (endDate.isBefore(startDate)) {
                l.add("Event end date must be after event start date");
            }
            for (Event event : eventRepository.findAll()) {
                LocalDateTime eventStartDate = LocalDateTime.parse(event.getStartDate(), formatter);
                LocalDateTime eventEndDate = LocalDateTime.parse(event.getEndDate(), formatter);
                if(!(eventStartDate.isAfter(endDate) || startDate.isAfter(eventEndDate))){
                   l.add("Wrong planning.Event intersects with event " + event.getName());
                }
            }
        }
        if(l.size() == 0) {
            l.add("OK");
        }
        return l;
    }
}
