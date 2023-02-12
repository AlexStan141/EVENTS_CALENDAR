package com.example.BACKEND.mapper;

import com.example.BACKEND.dto.insert.EventInsertDto;
import com.example.BACKEND.dto.response.EventResponseDto;
import com.example.BACKEND.model.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMappers {

    public Event eventInsertDtoToEventMapper(EventInsertDto eventInsertDto){
        return new Event(eventInsertDto.getName(), eventInsertDto.getStartDate(), eventInsertDto.getEndDate());
    }

    public EventResponseDto eventToEventResponseDtoMapper(Event event){
        return new EventResponseDto(event.getId(), event.getName(), event.getStartDate(), event.getEndDate());
    }
}
