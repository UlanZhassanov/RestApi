package com.ulanzhassanov.RestApi.controller;

import com.ulanzhassanov.RestApi.model.Event;
import com.ulanzhassanov.RestApi.repository.hibernate.EventRepositoryImpl;
import com.ulanzhassanov.RestApi.service.EventService;

import java.util.List;

public class EventController {
    private EventService eventService = new EventService(new EventRepositoryImpl());

    public Event saveEvent(Event event) {return eventService.saveEvent(event);}
    public Event getEventById(int id) {return eventService.getEventById(id);}
    public List<Event> getAllEvents() {return eventService.getAllEvents();}
    public Event updateEvent(Event event) {return eventService.updateEvent(event);}
    public void deleteEventById(int id) {eventService.deleteEventById(id);}

}
