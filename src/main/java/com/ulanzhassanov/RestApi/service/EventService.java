package com.ulanzhassanov.RestApi.service;

import com.ulanzhassanov.RestApi.model.Event;
import com.ulanzhassanov.RestApi.repository.EventRepository;

import java.util.List;

public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event getEventById(Integer id) {
        return eventRepository.getById(id);
    }

    public Event getEventByFileId(Integer id) {
        return eventRepository.getByFileId(id);
    }

    public List<Event> getAllEvents() {
        return eventRepository.getAll();
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Event event) {
        return eventRepository.update(event);
    }

    public void deleteEventById(Integer id){
        eventRepository.deleteById(id);
    }
}
