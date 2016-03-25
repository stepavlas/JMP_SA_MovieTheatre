package com.stepavlas.movieTheatre;

import com.stepavlas.movieTheatre.exceptions.IncorrectEventException;
import com.stepavlas.movieTheatre.exceptions.IncorrectShowException;

import java.util.List;

/**
 * Created by Степан on 21.03.2016.
 */
public interface ShowDao {
    void add(Show show) throws IncorrectEventException, IncorrectShowException;

    Ticket addTicket(Show show, int row, int place) throws IncorrectShowException;

    public Show update(Show show) throws IncorrectShowException, IncorrectEventException;

    public Show remove(Show show) throws IncorrectShowException, IncorrectEventException;

    public Show getShow(Show show);

    Show findShow(Show show);

    List<Show> getEventShedule(Event event) throws IncorrectEventException;
}
