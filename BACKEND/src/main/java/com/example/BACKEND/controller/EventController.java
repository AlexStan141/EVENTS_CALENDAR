package com.example.BACKEND.controller;

import com.example.BACKEND.ObjectWrapper;
import com.example.BACKEND.dto.insert.EventInsertDto;
import com.example.BACKEND.dto.response.EventResponseDto;
import com.example.BACKEND.dto.update.EventUpdateDto;
import com.example.BACKEND.model.Event;
import com.example.BACKEND.service.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("event")
@RestController
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ObjectWrapper<String, List<EventResponseDto>> getAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping(path="id/{id}")
    public ObjectWrapper<String,EventResponseDto> getEventById(@PathVariable("id") Long id){
        return eventService.getEventById(id);
    }

    @GetMapping(path="table/{date}")
    public List<List<String>> eventTable(@PathVariable("date") String middleDay){
        return eventService.eventTable(middleDay);
    }

    @GetMapping(path="{date}")
    public EventResponseDto getEvent(@PathVariable("date") String date){
        return eventService.getEvent(date);
    }

    @PostMapping()
    public ObjectWrapper<List<String>, EventResponseDto> insertEvent(@RequestBody EventInsertDto eventInsertDto){
        return eventService.insertEvent(eventInsertDto);
    }

    @PutMapping
    public ObjectWrapper<List<String>, EventResponseDto> updateEvent(@RequestBody EventUpdateDto eventUpdateDto){
        return eventService.updateEvent(eventUpdateDto);
    }

    @DeleteMapping(path="{id}")
    public ObjectWrapper<String,EventResponseDto> deleteEvent(@PathVariable("id") Long id){
        return eventService.deleteEvent(id);
    }

    @GetMapping(path="names")
    public List<String> getNames(){
        return eventService.getNames();
    }

    @GetMapping(path="idFromName/{name}")
    public Optional<Long> findIdFromName(@PathVariable("name") String name){
        return eventService.findIdFromName(name);
    }

    @GetMapping(path="ordered")
    public List<Event> getOrderedEvents(){
        return eventService.getOrderedEvents();
    }
}
