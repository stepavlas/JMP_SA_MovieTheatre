package com.stepavlas.aspects;

import com.stepavlas.movieTheatre.Event;
import com.stepavlas.movieTheatre.EventDao;
import com.stepavlas.movieTheatre.exceptions.IncorrectEventException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Степан on 29.03.2016.
 */

@Aspect
public class CounterAspect {
    EventDao eventDao;
    Map<Event, Integer> accessedByName = new HashMap<>();
    Map<Event, Integer> queTicketPrice = new HashMap<>();
    Map<Event, Integer> bookedTickets = new HashMap<>();

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Pointcut("execution(public com.stepavlas.movieTheatre.Event *.getByName(..))")
    private void countByNameMethod(){}

    @Pointcut("execution(public com.stepavlas.movieTheatre.Ticket *.bookTicket(com.stepavlas.movieTheatre.Event, ..))" +
            "|| execution(public * *.getTicketPrice(com.stepavlas.movieTheatre.Event, ..))")
    private void countBooking(){}

    @AfterReturning(pointcut = "countByNameMethod()", returning = "retEvent")
    public void countFindEventByName(Object retEvent){
        Event event = (Event)retEvent;
        putToMap(accessedByName, event);
        System.out.println(accessedByName);
    }

    @AfterReturning(pointcut = "countBooking() && args(argEvent, ..)")
    public void countGetTicketPrice(JoinPoint joinPoint, Object argEvent){
        Event event = (Event)argEvent;
        try {
            event = eventDao.findEvents(event).get(0);
            String methodName = joinPoint.getSignature().getName();
            if (methodName.equals("bookTicket")) {
                putToMap(queTicketPrice, event);
                System.out.println(queTicketPrice);
            } else if (methodName.equals("getTicketPrice")){
                putToMap(bookedTickets, event);
                System.out.println(bookedTickets);
            }
        } catch (IncorrectEventException e) {
            e.printStackTrace();
        }
    }

    private void putToMap(Map<Event, Integer> map, Event event) {
        Integer count = map.get(event);
        if (count == null){
            count = 0;
        }
        map.put(event, ++count);
    }
}
