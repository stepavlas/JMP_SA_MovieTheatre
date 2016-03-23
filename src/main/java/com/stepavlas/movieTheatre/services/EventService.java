package com.stepavlas.movieTheatre.services;

import com.stepavlas.movieTheatre.dao.EventDao;
import com.stepavlas.movieTheatre.dao.ShowDao;
import com.stepavlas.movieTheatre.entities.Auditorium;
import com.stepavlas.movieTheatre.entities.Event;
import com.stepavlas.movieTheatre.entities.Show;
import com.stepavlas.movieTheatre.exceptions.IncorrectEventException;
import com.stepavlas.movieTheatre.exceptions.IncorrectShowException;

import java.util.Date;
import java.util.List;

/**
 * Created by Степан on 21.03.2016.
 */
public class EventService {
    EventDao eventDao;
    ShowDao showDao;

    public void createEvent(String eventName, int year, String country, String director, int basePrice){
        Event event = new Event(eventName, year, country, director, basePrice);
        try {
            eventDao.add(event);
        } catch (IncorrectEventException e){
            System.out.println("Error while creating event with such data with message: " + e.getMessage());
        }
    }

    public Event remove(String eventName){
        Event foundEvent = getByName(eventName);
        try {
            return eventDao.remove(foundEvent);
        } catch (IncorrectEventException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Event getByName(String eventName){
        Event event = new Event();
        event.setEventName(eventName);
        List<Event> events = null;
        try {
            events = eventDao.findEvents(event);
        } catch (IncorrectEventException e){
            e.printStackTrace();
        }
        if (events == null || events.isEmpty() || events.size() == 0 || events.size() > 1){
            System.out.println("No events or more than one event were found. Can't find event with such name");
            return null;
        }
        return events.get(0);
    }

    public List<Event> getAll(){
        return eventDao.getAll();
    }

    public boolean assignAuditorium(String eventName, Auditorium auditorium, Date date){
        Event event = getByName(eventName);
        if (event == null){
            return false;
        }
        Show show = new Show(event, date, auditorium);
        try {
            showDao.add(show);
            return true;
        } catch (IncorrectEventException | IncorrectShowException e) {
            e.printStackTrace();
            return false;
        }
    }
}
