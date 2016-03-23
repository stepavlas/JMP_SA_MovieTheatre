package com.stepavlas.movieTheatre.services;

import com.stepavlas.movieTheatre.dao.EventDao;
import com.stepavlas.movieTheatre.dao.ShowDao;
import com.stepavlas.movieTheatre.dao.UserDao;
import com.stepavlas.movieTheatre.entities.*;
import com.stepavlas.movieTheatre.exceptions.IncorrectEventException;
import com.stepavlas.movieTheatre.exceptions.IncorrectShowException;
import com.stepavlas.movieTheatre.exceptions.IncorrectUserException;
import com.stepavlas.movieTheatre.services.discount.DiscountService;
import com.stepavlas.movieTheatre.services.discount.DiscountStrategy;

import java.util.Date;
import java.util.List;

/**
 * Created by Степан on 24.03.2016.
 */
public class BookingService {
    DiscountService discountService;
    ShowDao showDao;
    EventDao eventDao;
    UserDao userDao;

    public int getTicketPrice(Event event, Date date, int row, int place, User user) throws IncorrectEventException {
        List<Event> foundEvents = eventDao.findEvents(event);
        if (foundEvents.size() != 1){
            System.out.println("No events were found or more than 1 event was found. Specify event correctly");
            return -1;
        }
        Event dbEvent = foundEvents.get(0);

        Show show = new Show();
        show.setEvent(dbEvent);
        show.setDateTime(date);
        Show dbShow = showDao.getShow(show);

        int discount = discountService.getDiscount(user, dbShow);
        double price = dbEvent.getBasePrice() * discount / 100;
        if (event.getRating() == Rating.HIGh){
            price = 1.2 * price;
        }

        Ticket ticket = new Ticket(dbShow, row, place);
        int seat = ticket.getPlace();
        String vipSeats = dbShow.getAuditorium().getVipSeats();
        if (vipSeats.contains(Integer.toString(seat))){
            price = 2 * price;
        }

        return (int)price;
    }

    public List<Ticket> getTicketsForEvent(Event event, Date date) throws IncorrectEventException {
        List<Event> foundEvents = eventDao.findEvents(event);
        if (foundEvents.size() != 1){
            System.out.println("No events were found or more than 1 event was found. Specify event correctly");
            return null;
        }
        Event dbEvent = foundEvents.get(0);

        Show show = new Show();
        show.setEvent(dbEvent);
        show.setDateTime(date);
        Show dbShow = showDao.getShow(show);

        return dbShow.getPurchasedSeats();
    }

    public Ticket bookTicket(Event event, User user, Date date, int row, int place)
            throws IncorrectEventException, IncorrectShowException, IncorrectUserException {
        List<Event> foundEvents = eventDao.findEvents(event);
        if (foundEvents.size() != 1){
            System.out.println("No events were found or more than 1 event was found. Specify event correctly");
            return null;
        }
        Event dbEvent = foundEvents.get(0);

        Show show = new Show();
        show.setEvent(dbEvent);
        show.setDateTime(date);
        Show dbShow = showDao.getShow(show);
        Ticket ticket = showDao.addTicket(dbShow, row, place);
        List<User> users = userDao.findUsers(user);
        if (users.size() == 1) {
            int numOfTickets = user.getNumTickets();
            user.setNumTickets(++numOfTickets);
            userDao.update(user);
        } else if (users.size() != 0) {
            System.out.println("More than 1 user was found. Specify user correctly");
            return null;
        }
        return ticket;
    }
}
