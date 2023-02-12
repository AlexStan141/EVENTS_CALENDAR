package com.example.BACKEND.service.interfaces;

import com.example.BACKEND.ObjectWrapper;
import com.example.BACKEND.dto.insert.EventInsertDto;
import com.example.BACKEND.dto.response.EventResponseDto;
import com.example.BACKEND.dto.update.EventUpdateDto;
import com.example.BACKEND.model.Event;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface EventService {

    ObjectWrapper<String,List<EventResponseDto>> getAllEvents();
    ObjectWrapper<String,EventResponseDto> getEventById(Long id);
    EventResponseDto getEvent(String dateTime);
    List<List<String>> eventTable(String middleDay);
    ObjectWrapper<List<String>,EventResponseDto> insertEvent(EventInsertDto eventInsertDto);
    ObjectWrapper<List<String>,EventResponseDto> updateEvent(EventUpdateDto eventUpdateDto);
    ObjectWrapper<String,EventResponseDto> deleteEvent(Long id);
    List<String> getNames();
    Optional<Long> findIdFromName(String name);
    List<Event> getOrderedEvents();
}
