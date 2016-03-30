package com.stepavlas.movieTheatre;

import com.stepavlas.movieTheatre.exceptions.IncorrectEventException;
import com.stepavlas.movieTheatre.exceptions.IncorrectShowException;
import com.stepavlas.movieTheatre.exceptions.IncorrectUserException;
import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Степан on 25.03.2016.
 */
public class ViewApp {
    Calendar calendar;
    UserService userService;

    public ViewApp(Calendar calendar, UserService userService) {
        this.calendar = calendar;
        this.userService = userService;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("theatreConfig.xml");
        ViewApp app = ctx.getBean("viewApp", ViewApp.class);
        app.calendar.set(1983, Calendar.JULY, 20);
        app.calendar.set(Calendar.HOUR_OF_DAY, 9);
        Date date = app.calendar.getTime();

        UserService userService = app.userService;

        User user = registerUser(date, userService);

        AuditoriumService auditoriumService = ctx.getBean("auditoriumService", AuditoriumService.class);
        System.out.println(auditoriumService.getAuditoriums());

        EventService eventService = ctx.getBean("eventService", EventService.class);
        eventService.createEvent("Grimsby", 2016, "Great Britain", "Louis Leterrier", 150);
        eventService.createEvent("The Jungle Book", 2016, "USA", "Jon Favreau", 200);
        eventService.createEvent("Louder Than Bombs", 2016, "Norway", "Joachim Trier", 150);

        app.calendar.set(2016, Calendar.FEBRUARY, 27);
        Date showTime = app.calendar.getTime();

        boolean success = eventService.assignAuditorium("Grimsby", auditoriumService.getAuditoriums().get(0), showTime);

        eventService.getByName("The Jungle Book");
        eventService.getByName("Grimsby");
        eventService.getByName("Grimsby");

        BookingService bookingService = ctx.getBean("bookingService", BookingService.class);

        Event event = new Event();
        event.setEventName("The Jungle Book");

        bookingService.getTicketPrice(event, showTime, 2, 4, user);

        user.setNumTickets(10);

        bookingService.getTicketPrice(event, showTime, 2, 4, user);
        bookingService.getTicketPrice(event, showTime, 2, 4, user);

        user.setBirthDate(showTime);
        user.setNumTickets(11);

        bookingService.getTicketPrice(event, showTime, 2, 4, user);

        try {
            bookingService.bookTicket(event, user, showTime, 2, 4);
        } catch (IncorrectEventException | IncorrectShowException | IncorrectUserException e) {
            e.printStackTrace();
        }

        System.out.println(success);
    }

    private static User registerUser(Date date, UserService userService) {
        userService.register("Sergei", "Chillintano", "serg@example.com", date);
        User user = userService.getUserByEmail("serg@example.com");
        System.out.println(user.getId());
        return user;
    }
}
