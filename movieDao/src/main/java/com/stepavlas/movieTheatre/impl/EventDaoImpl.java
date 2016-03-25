package com.stepavlas.movieTheatre.impl;
import com.google.common.annotations.VisibleForTesting;
import com.stepavlas.movieTheatre.Event;
import com.stepavlas.movieTheatre.EventDao;
import com.stepavlas.movieTheatre.exceptions.IncorrectEventException;

import java.util.*;

/**
 * Created by Степан on 19.03.2016.
 */
public class EventDaoImpl implements EventDao {
    private Map<Long, Event> events = new HashMap<>();

    @Override
    public void add(Event event) throws IncorrectEventException {
        validate(event);
        if (event.getId() != 0 || event.getEventName() == null || event.getYear() == 0 ||
                event.getDirector() == null || event.getCountry() == null || event.getBasePrice() == 0){
            throw new IncorrectEventException("Event doesn't have mandatory field for adding");
        }
        List<Event> dbEvents = findEvents(event);
        if (!dbEvents.isEmpty()) {
            throw new IllegalArgumentException("User already exists");
        }
        event.setId(events.size() + 1);
        events.put(event.getId(), event);
    }

    @Override
    public Event remove(Event event) throws IncorrectEventException {
        validate(event);
        if (event.getId() == 0){
            throw new IncorrectEventException("Evemt doesn't have mandatory field 'id' " +
                    "for operation remove");
        }
        Event removedEvent = events.remove(event.getId());
        return removedEvent;
    }

    @Override
    public Event update(Event event) throws IncorrectEventException {
        validate(event);
        if (event.getId() == 0 || event.getEventName() == null || event.getCountry() == null ||
                event.getDirector() == null || event.getYear() == 0 || event.getBasePrice() == 0){
            throw new IncorrectEventException("Event doesn't have mandatory field or has incorrect id " +
                    "for updating");
        }
        Event dbEvent = getById(event);
        if (dbEvent == null){
            return null;
        }
        event.setId(dbEvent.getId());
        events.put(event.getId(), event);
        return event;
    }

    @Override
    public List<Event> findEvents(Event event) throws IncorrectEventException {
        validate(event);
        List<Event> result = new ArrayList<>();
        if (event.getId() != 0){
            Event dbEvent = getById(event);
            if (dbEvent != null){
                result.add(dbEvent);
            }
        } else if (event.getEventName() != null){
            Event dbEvent = findByName(event);
            if (dbEvent != null){
                result.add(dbEvent);
            }
        } else if (event.getCountry() != null){
            result = findByCountry(event);
        } else if (event.getDirector() != null){
            result = findByDirector(event);
        } else if (event.getYear() != 0){
            result = findByYear(event);
        } else if (event.getRating() != null){
            result = findByRating(event);
        }
        return result;
    }

    private List<Event> findByRating(Event event){
        List<Event> result = new ArrayList<>();
        for (Event dbEvent : events.values()){
            if (dbEvent.getRating() != null && dbEvent.getRating() == event.getRating()){
                result.add(dbEvent);
            }
        }
        return result;
    }

    private Event findByName(Event event){
        for (Event dbEvent : events.values()){
            if (event.getEventName().equals(dbEvent.getEventName())){
                return dbEvent;
            }
        }
        return null;
    }

    private List<Event> findByCountry(Event event){
        List<Event> result = new ArrayList<>();
        for (Event dbEvent : events.values()){
            if (event.getCountry().equals(dbEvent.getCountry()) &&
                    event.getCountry().equals(dbEvent.getCountry())){
                result.add(dbEvent);
            }
        }
        return result;
    }

    private List<Event> findByYear(Event event){
        List<Event> result = new ArrayList<>();
        for (Event dbEvent : events.values()){
            if (event.getYear() == dbEvent.getYear()){
                result.add(dbEvent);
            }
        }
        return result;
    }



    private List<Event> findByDirector(Event event){
        List<Event> result = new ArrayList<>();
        for (Event dbEvent : events.values()){
            if (event.getDirector().equals(dbEvent.getDirector()) &&
                    event.getDirector().equals(dbEvent.getDirector())){
                result.add(dbEvent);
            }
        }
        return result;
    }

    private void validate(Event event) throws IncorrectEventException {
        if (event == null){
            throw new IllegalArgumentException("Event argument is null");
        }
        if (event.getId() < 0){
            throw new IncorrectEventException("Event has negative id");
        }
        int year = event.getYear();
        int curYear = Calendar.getInstance().get(Calendar.YEAR);
        if (year != 0 && (year < 1890 || year > curYear)){
            throw new IncorrectEventException("Event has incorrect year");
        }
    }

    @Override
    public Event getById(Event event) throws IncorrectEventException {
        validate(event);
        if (event.getId() == 0){
            throw new IncorrectEventException("User doesn't have id");
        }
        return events.get(event.getId());
    }

    @VisibleForTesting
    protected Map<Long, Event> getEvents() {
        return events;
    }

    @VisibleForTesting
    protected void setEvents(Map<Long, Event> events) {
        this.events = events;
    }

    @Override
    public List<Event> getAll() {
        List<Event> eventsList = new ArrayList<Event>(events.values());
        return eventsList;
    }
}
