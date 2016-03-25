package com.stepavlas.movieTheatre;

import com.stepavlas.movieTheatre.discount.DiscountService;
import com.stepavlas.movieTheatre.exceptions.IncorrectEventException;
import com.stepavlas.movieTheatre.exceptions.IncorrectShowException;
import com.stepavlas.movieTheatre.exceptions.IncorrectUserException;

import java.util.Date;
import java.util.List;

/**
 * Created by Степан on 24.03.2016.
 */
public class BookingService {
    private DiscountService discountService;
    private ShowDao showDao;
    private EventDao eventDao;
    private UserDao userDao;

    public int getTicketPrice(Event event, Date date, int row, int place, User user) {
        try {
            Event dbEvent = findEvent(event);
            Show dbShow = findShow(dbEvent, date);

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
        } catch (IncorrectEventException iee){
            System.out.println(iee.getMessage());
            return -1;
        }
    }

    public List<Ticket> getTicketsForEvent(Event event, Date date){
        try {
            Event dbEvent = findEvent(event);
            Show dbShow = findShow(dbEvent, date);
            return dbShow.getPurchasedSeats();
        } catch (IncorrectEventException iee){
            System.out.println(iee.getMessage());
            return null;
        }
    }

    public Ticket bookTicket(Event event, User user, Date date, int row, int place)
            throws IncorrectEventException, IncorrectShowException, IncorrectUserException {
        try {
            Event dbEvent = findEvent(event);
            Show dbShow = findShow(dbEvent, date);
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
        } catch (IncorrectEventException iee){
            System.out.println(iee.getMessage());
            return null;
        }
    }

    private Event findEvent(Event event) throws IncorrectEventException{
        List<Event> foundEvents = eventDao.findEvents(event);
        if (foundEvents.size() != 1){
            throw new IncorrectEventException("No events were found or more than 1 event was found. Specify event correctly");
        }
        return foundEvents.get(0);
    }

    private Show findShow(Event dbEvent, Date date) throws IncorrectEventException{
        Show show = new Show();
        show.setEvent(dbEvent);
        show.setDateTime(date);
        return showDao.getShow(show);
    }
}
