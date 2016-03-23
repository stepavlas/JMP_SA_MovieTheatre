package com.stepavlas.movieTheatre.dao.impl;

import com.stepavlas.movieTheatre.dao.EventDao;
import com.stepavlas.movieTheatre.entities.Auditorium;
import com.stepavlas.movieTheatre.entities.Event;
import com.stepavlas.movieTheatre.entities.Show;
import com.stepavlas.movieTheatre.exceptions.IncorrectEventException;
import com.stepavlas.movieTheatre.exceptions.IncorrectShowException;
import com.stepavlas.movieTheatre.services.AuditoriumService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

/**
 * Created by Степан on 22.03.2016.
 */
public class ShowDaoTest {
    private static ShowDaoImpl showDao = new ShowDaoImpl();
    private static EventDaoImpl eventDao = new EventDaoImpl();

    @BeforeClass
    public static void initEvents(){
        Map<Long, Event> events = new HashMap<>();
        Event event1 = new Event(1, "What Women Want", 2000, "USA", "Nancy Meyers", 100);
        events.put(event1.getId(), event1);
        Event event2 = new Event(2, "Allegiant", 2016, "USA", "Robert Schwentke", 100);
        events.put(event2.getId(), event2);
        Event event3 = new Event(3, "Grimsby", 2016, "Great Britain", "Louis Leterrier", 150);
        events.put(event3.getId(), event3);
        eventDao.setEvents(events);
        showDao.setEventDao(eventDao);
    }

    @Before
    public void init(){
        showDao.getShows().clear();
    }

    @Test
    public void testAddShow(){
        try {
            Show show = createShow(1, "hall1", 10);
            showDao.add(show);
            Assert.assertTrue(showDao.getShows().values().contains(show));
        } catch (IncorrectEventException | IncorrectShowException e){
            e.printStackTrace();
            Assert.fail();
        }
    }

    private Show createShow(long eventId, String hallName, int hours) throws IncorrectEventException {
        Event event = new Event();
        event.setId(eventId);
        Event dbEvent = eventDao.getById(event);
        Auditorium auditorium = new Auditorium(hallName, 140, "E12,E13,E14,E15,F12,F13,F14,F15");
        Calendar calendar = Calendar.getInstance();
        calendar.set(25, Calendar.MARCH, 25, hours, 30);
        Date date = calendar.getTime();
        return new Show(dbEvent, date, auditorium);
    }

    @Test
    public void testAddShowSame(){
        try {
            Show show = createShow(1, "hall1", 10);
            showDao.add(show);
            show = createShow(2, "hall1", 11);
            showDao.add(show);
            Assert.fail("No exceptions were thrown");
        } catch (IncorrectShowException iee){
            Assert.assertTrue(true);
        } catch (IncorrectEventException iee){
            Assert.fail("IncorrectEventException was thrown instead IncorrectShowException");
        }
    }

    @Test
    public void testUpdateShow(){
        try {
            Show show = createShow(1, "hall1", 10);
            showDao.add(show);
            show = createShow(2, "hall1", 11);
            showDao.update(show);
            Assert.assertEquals(1, showDao.getShows().size());
            Assert.assertTrue(showDao.getShows().values().contains(show));
        } catch (IncorrectShowException | IncorrectEventException e){
            Assert.fail("IncorrectEventException or IncorrectShowException was thrown");
        }
    }
}
