package com.stepavlas.movieTheatre.services;

import com.stepavlas.movieTheatre.entities.Event;
import com.stepavlas.movieTheatre.entities.Ticket;
import com.stepavlas.movieTheatre.entities.User;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 17.03.2016.
 */
public interface BookingService {
    double getTicketPrice(Event event, String date, String time, String seats, User user);
    boolean bookTicket(User user, Ticket ticket);
    List<Ticket> getTicketsForEvent(Event event, Date date);

}
