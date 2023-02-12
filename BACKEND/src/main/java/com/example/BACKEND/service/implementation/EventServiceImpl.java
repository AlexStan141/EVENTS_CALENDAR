package com.example.BACKEND.service.implementation;

import com.example.BACKEND.ObjectWrapper;
import com.example.BACKEND.dto.insert.EventInsertDto;
import com.example.BACKEND.dto.response.EventResponseDto;
import com.example.BACKEND.dto.update.EventUpdateDto;
import com.example.BACKEND.mapper.EventMappers;
import com.example.BACKEND.model.Event;
import com.example.BACKEND.repository.EventRepository;
import com.example.BACKEND.service.interfaces.EventService;
import com.example.BACKEND.utils.EventInsertDtoValidator;
import com.example.BACKEND.utils.EventUpdateDtoValidator;
import com.example.BACKEND.utils.StringDateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMappers eventMapper;
    private final StringDateValidator stringDateValidator;
    private final EventInsertDtoValidator eventInsertDtoValidator;
    private final EventUpdateDtoValidator eventUpdateDtoValidator;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, EventMappers eventMapper, StringDateValidator stringDateValidator, EventInsertDtoValidator eventInsertDtoValidator, EventUpdateDtoValidator eventUpdateDtoValidator) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.stringDateValidator = stringDateValidator;
        this.eventInsertDtoValidator = eventInsertDtoValidator;
        this.eventUpdateDtoValidator = eventUpdateDtoValidator;
    }
    public ObjectWrapper<String, List<EventResponseDto>> getAllEvents(){
        List<EventResponseDto> eventResponseDtoList = eventRepository.findAll().stream()
                .map(eventMapper::eventToEventResponseDtoMapper)
                .collect(Collectors.toList());
        if(eventResponseDtoList.size()!=0) {
            return new ObjectWrapper<>(200,"Events found!",eventResponseDtoList);
        }
        else{
            return null;
        }
    }

    @Override
    public ObjectWrapper<String, EventResponseDto> getEventById(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if(eventOptional.isPresent()){
            EventResponseDto eventResponseDto = eventMapper.eventToEventResponseDtoMapper(eventOptional.get());
            return new ObjectWrapper<>(200,"Event found",eventResponseDto);
        }
        else{
            return new ObjectWrapper<>(404,"Event not found",null);
        }
    }

    public EventResponseDto getEvent(String dateTime){
        List<Event> eventList = eventRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if(stringDateValidator.dateTimeIsValid(dateTime)) {
            LocalDateTime dateLocalDateTime = LocalDateTime.parse(dateTime, formatter);
            for (Event event : eventList) {
                LocalDateTime startDateLocalDateTime = LocalDateTime.parse(event.getStartDate(), formatter);
                LocalDateTime endDateLocalDateTime = LocalDateTime.parse(event.getEndDate(), formatter);
                if (!dateLocalDateTime.isAfter(endDateLocalDateTime) &&
                        !dateLocalDateTime.isBefore(startDateLocalDateTime)) {
                    return eventMapper.eventToEventResponseDtoMapper(event);
                }
            }
            return new EventResponseDto(null,null,dateTime,dateTime);
        }
        else{
            return null;
        }
    }

    public String getDay(String date,int nrDays){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date,formatter);
        LocalDate tomorrowDate =  localDate.plusDays(nrDays);
        return tomorrowDate.toString();
    }

    @Override
    public List<List<String>> eventTable(String middleDay) {
        List<List<String>> table = new ArrayList<>();
        List<String> line = new ArrayList<>();
        line.add("");
        for(int i=0; i<=23; i++){
            line.add(String.valueOf(i));
        }
        table.add(line);
        for(int i=-3; i<=3;i++){
            line = new ArrayList<>();
            line.add(getDay(middleDay,i));
            for(int j=0;j<=23;j++){
                String dateTime;
                if(j<10){
                    dateTime = getDay(middleDay, i) + " 0" + j + ":00";
                }
                else{
                    dateTime = getDay(middleDay, i) + " " + j + ":00";
                }
                if(getEvent(dateTime).getId() == null){
                    line.add("");
                }
                else{
                    line.add(getEvent(dateTime).getName());
                }
            }
            table.add(line);
        }
        return table;
    }

    @Override
    public ObjectWrapper<List<String>,EventResponseDto> insertEvent(EventInsertDto eventInsertDto) {
        List<String> validInsertion = new ArrayList<>();
        validInsertion.add("OK");
        if(Objects.equals(eventInsertDtoValidator.isValid(eventInsertDto), validInsertion)){
            Event event = eventMapper.eventInsertDtoToEventMapper(eventInsertDto);
            eventRepository.save(event);
            EventResponseDto eventResponseDto = eventMapper.eventToEventResponseDtoMapper(event);
            List<String> OK = new ArrayList<>();
            OK.add("Event inserted successfully");
            return new ObjectWrapper<>(200,OK,eventResponseDto);
        }
        else{
            return new ObjectWrapper<>(400,eventInsertDtoValidator.isValid(eventInsertDto),null);
        }
    }

    @Override
    public ObjectWrapper<List<String>, EventResponseDto> updateEvent(EventUpdateDto eventUpdateDto) {
        Optional<Event> eventOptional = eventRepository.findById(eventUpdateDto.getId());
        if(eventOptional.isPresent()){
            List<String> validUpdate = new ArrayList<>();
            validUpdate.add("OK");
            if(Objects.equals(eventUpdateDtoValidator.isValid(eventUpdateDto), validUpdate)){
                eventOptional.get().setName(eventUpdateDto.getName());
                eventOptional.get().setStartDate(eventUpdateDto.getStartDate());
                eventOptional.get().setEndDate(eventUpdateDto.getEndDate());
                eventRepository.save(eventOptional.get());
                EventResponseDto eventResponseDto = eventMapper.eventToEventResponseDtoMapper(eventOptional.get());
                List<String> OK = new ArrayList<>();
                OK.add("Event updated successfully");
                return new ObjectWrapper<>(200,OK,eventResponseDto);
            }
            else{
                return new ObjectWrapper<>(400,eventUpdateDtoValidator.isValid(eventUpdateDto),null);
            }
        }
        else{
            List<String> invalidUpdate = new ArrayList<>();
            invalidUpdate.add("Id not found");
            return new ObjectWrapper<>(404,invalidUpdate,null);
        }
    }

    @Override
    public ObjectWrapper<String, EventResponseDto> deleteEvent(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if(eventOptional.isPresent()){
            eventRepository.deleteById(id);
            EventResponseDto eventResponseDto = eventMapper.eventToEventResponseDtoMapper(eventOptional.get());
            return new ObjectWrapper<>(200,"Event deleted successfully!",eventResponseDto);
        }
        else{
            return new ObjectWrapper<>(404,"Id not found",null);
        }
    }

    public List<String> getNames(){
        return eventRepository.getNames();
    }

    public Optional<Long> findIdFromName(String name){
        System.out.println(eventRepository.getIdFromName(name));
        return eventRepository.getIdFromName(name);
    }

    public List<Event> getOrderedEvents(){
        return eventRepository.getOrderedEvents();
    }
}
