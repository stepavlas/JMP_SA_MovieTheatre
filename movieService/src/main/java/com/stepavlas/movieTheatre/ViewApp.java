package com.stepavlas.movieTheatre;

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

        registerUser(date, userService);

        AuditoriumService auditoriumService = ctx.getBean("auditoriumService", AuditoriumService.class);
        System.out.println(auditoriumService.getAuditoriums());

        EventService eventService = ctx.getBean("eventService", EventService.class);
        eventService.createEvent("Grimsby", 2016, "Great Britain", "Louis Leterrier", 150);

        app.calendar.set(2016, 02, 27);
        Date showTime = app.calendar.getTime();

        boolean success = eventService.assignAuditorium("Grimsby", auditoriumService.getAuditoriums().get(0), showTime);

        System.out.println(success);
    }

    private static void registerUser(Date date, UserService userService) {
        userService.register("Sergei", "Chillintano", "serg@example.com", date);
        User user = userService.getUserByEmail("serg@example.com");
        System.out.println(user.getId());
    }
}
