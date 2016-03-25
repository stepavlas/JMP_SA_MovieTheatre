package com.stepavlas.movieTheatre.impl;

import com.google.common.annotations.VisibleForTesting;
import com.stepavlas.movieTheatre.*;
import com.stepavlas.movieTheatre.exceptions.IncorrectEventException;
import com.stepavlas.movieTheatre.exceptions.IncorrectShowException;

import java.util.*;

/**
 * Created by Степан on 22.03.2016.
 */
public class ShowDaoImpl implements ShowDao {
    private Map<Long, Show> shows = new HashMap<>();
    private EventDao eventDao;

    public ShowDaoImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public void add(Show show) throws IncorrectShowException, IncorrectEventException {
        validate(show);
        if (findShow(show) != null){
            throw new IncorrectShowException("There is already another event at this date and in the same auditorium");
        }
        show.setId(shows.size());
        shows.put(show.getId(), show);
    }

    @Override
    public Show update(Show show) throws IncorrectShowException, IncorrectEventException {
        validate(show);
        Show dbShow = findShow(show);
        if (dbShow == null){
            return null;
        }
        show.setId(dbShow.getId());
        shows.put(show.getId(), show);
        return show;
    }

    @Override
    public Show remove(Show show) throws IncorrectShowException, IncorrectEventException {
        if (show.getAuditorium() == null || show.getDateTime() == null){
            throw new IncorrectShowException("Mandatory fields are absent. Can't delete show");
        }
        Show dbShow = findShow(show);
        if (dbShow == null){
            return null;
        }
        shows.remove(dbShow.getId());
        return dbShow;
    }

    private void validate(Show show) throws IncorrectEventException, IncorrectShowException {
        if (eventDao.getById(show.getEvent()) == null){
            throw new IllegalArgumentException("Can't add event which doesn't exist");
        }
        if (show.getAuditorium() == null || show.getDateTime() == null){
            throw new IncorrectShowException("Mandatory fields are absent. Can't add or update show");
        }
    }

    @Override
    public Show getShow(Show show){
        if (show == null){
            throw new IllegalArgumentException("Show is null");
        }
        for (Show dbShow: shows.values()){
            if (show.equals(dbShow)){
                return dbShow;
            }
        }
        return null;
    }

    @Override
    public Ticket addTicket(Show show, int row, int place) throws IncorrectShowException{
        Show dbShow = findShow(show);
        if (show == null){
            throw new IncorrectShowException("No such show");
        }
        Ticket ticket = new Ticket(show, row, place);
        if (ticket.getPlace() > dbShow.getAuditorium().getNumberOfSeats()){
            throw new IncorrectShowException("Auditorium " + dbShow.getAuditorium().getName() + " don't have such seat");
        }
        if (show.getPurchasedSeats().contains(ticket.getPlace())){
            throw new IncorrectShowException("seat: row " + row + " place " + place + " is purchased");
        }
        show.getPurchasedSeats().add(ticket);
        return ticket;
    }


    // searches show by auditorium and time or by event and time
    @Override
    public Show findShow(Show show){
        for (Show dbShow: shows.values()) {
            if ((show.getAuditorium() != null && show.getAuditorium().equals(dbShow.getAuditorium())
                    && hasSameTime(show, dbShow)) || (show.getEvent() != null && hasSameTime(show, dbShow))) {
                return show;
            }
        }
        return null;
    }

    @Override
    public List<Show> getEventShedule(Event event) throws IncorrectEventException{
        if (event.getId() == 0){
            throw new IncorrectEventException("Incorrect argument. Event doesn't have id");
        }
        List<Show> result = new ArrayList<>();
        for (Show dbShow: shows.values()){
            if (dbShow.getEvent().equals(event)){
                result.add(dbShow);
            }
        }
        return result;
    }



    private boolean hasSameTime(Show show1, Show show2){
        if (show1.getDateTime() == null || show2.getDateTime() == null) {
            return false;
        }
        long timeDifference = Math.abs(show1.getDateTime().getTime() - show2.getDateTime().getTime());
        return timeDifference <= 10800000;
    }

    @VisibleForTesting
    protected void setEventDao(EventDao eventDao){
        this.eventDao = eventDao;
    }

    @VisibleForTesting
    protected void setShows(Map<Long, Show> shows) {
        this.shows = shows;
    }

    @VisibleForTesting

    public Map<Long, Show> getShows() {
        return shows;
    }
}
