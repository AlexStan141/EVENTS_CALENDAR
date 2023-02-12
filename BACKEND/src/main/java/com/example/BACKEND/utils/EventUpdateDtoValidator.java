package com.example.BACKEND.utils;

import com.example.BACKEND.dto.insert.EventInsertDto;
import com.example.BACKEND.dto.update.EventUpdateDto;
import com.example.BACKEND.model.Event;
import com.example.BACKEND.repository.EventRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class EventUpdateDtoValidator {

    private StringDateValidator stringDateValidator;
    private EventRepository eventRepository;

    public EventUpdateDtoValidator(StringDateValidator stringDateValidator, EventRepository eventRepository) {
        this.stringDateValidator = stringDateValidator;
        this.eventRepository = eventRepository;
    }

    public List<String> isValid(EventUpdateDto eventUpdateDto) {
        List<String> l = new ArrayList<>();
        if (Objects.equals(eventUpdateDto.getName(), "")) {
            l.add("Event must have a name!");
        }
        if (!stringDateValidator.dateTimeIsValid(eventUpdateDto.getStartDate())) {
            l.add("Invalid start date format.Use format yyyy-MM-dd");
        }
        if (!stringDateValidator.dateTimeIsValid(eventUpdateDto.getEndDate())) {
            l.add("Invalid end date format.Use format yyyy-MM-dd");
        }
        if(!l.contains("Invalid start date format.Use format yyyy-MM-dd") &&
                !l.contains("Invalid end date format.Use format yyyy-MM-dd")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDate = LocalDateTime.parse(eventUpdateDto.getStartDate(), formatter);
            LocalDateTime endDate = LocalDateTime.parse(eventUpdateDto.getEndDate(), formatter);
            LocalDateTime nowDate = LocalDateTime.now();
            if (startDate.isBefore(nowDate)) {
                l.add("You cannot plan events in the past!");
            }
            if (endDate.isBefore(startDate)) {
                l.add("Event end date must be after event start date");
            }
            for (Event event : eventRepository.findAll()) {
                if(!Objects.equals(event.getId(), eventUpdateDto.getId())) {
                    LocalDateTime eventStartDate = LocalDateTime.parse(event.getStartDate(), formatter);
                    LocalDateTime eventEndDate = LocalDateTime.parse(event.getEndDate(), formatter);
                    if (!(eventStartDate.isAfter(endDate) || startDate.isAfter(eventEndDate))) {
                        l.add("Wrong planning.Event intersects with event " + event.getName());
                    }
                }
            }
        }
        if(l.size() == 0) {
            l.add("OK");
        }
        return l;
    }
}
