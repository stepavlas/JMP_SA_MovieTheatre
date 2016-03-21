package com.stepavlas.movieTheatre.dao;

import com.stepavlas.movieTheatre.entities.Event;
import com.stepavlas.movieTheatre.exceptions.IncorrectEventException;

import java.util.List;

/**
 * Created by Степан on 19.03.2016.
 */
public interface EventDao {
    void add(Event event) throws IncorrectEventException;

    Event remove(Event event) throws IncorrectEventException;

    Event update(Event event) throws IncorrectEventException;

    List<Event> findEvents(Event event) throws IncorrectEventException;

    Event getById(Event event) throws IncorrectEventException;

    List<Event> getAll();
}
