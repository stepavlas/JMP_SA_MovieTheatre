package com.stepavlas.movieTheatre.services;

import com.stepavlas.movieTheatre.entities.Auditorium;
import com.stepavlas.movieTheatre.entities.Event;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 17.03.2016.
 */
public interface EventService {
    void create();
    Event remove();
    Event getByName();
    List<Event> getAll();
    void assignAuditorium(Event event, Auditorium auditorium, Date date);
}
