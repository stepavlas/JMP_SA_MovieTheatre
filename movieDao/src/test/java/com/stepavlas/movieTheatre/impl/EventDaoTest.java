package com.stepavlas.movieTheatre.impl;

import com.stepavlas.movieTheatre.Event;
import com.stepavlas.movieTheatre.EventDao;
import com.stepavlas.movieTheatre.Rating;
import com.stepavlas.movieTheatre.exceptions.IncorrectEventException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 17.03.2016.
 */
public class EventDaoTest {
    private static EventDaoImpl eventDao = new EventDaoImpl();

    @Before
    public void init(){
        Map<Long, Event> events = new HashMap<>();
        Event event1 = new Event(1, "What Women Want", 2000, "USA", "Nancy Meyers", 100);
        events.put(event1.getId(), event1);
        Event event2 = new Event(2, "Allegiant", 2016, "USA", "Robert Schwentke", 100);
        events.put(event2.getId(), event2);
        Event event3 = new Event(3, "Grimsby", 2016, "Great Britain", "Louis Leterrier", 150);
        events.put(event3.getId(), event3);
        Event event4 = new Event(4, "Jane Got a Gun", 2016, "USA", "Gavin O'Connor", 100);
        events.put(event4.getId(), event4);
        Event event5 = new Event(5, "Hail, Caesar!", 2016, "Great Britain", "Joel Coen", 200);
        events.put(event5.getId(), event5);
        eventDao.setEvents(events);
    }


    @Test
    public void findByIdTest(){
        Event event = new Event();
        event.setId(4);
        try {
            Event actual = eventDao.getById(event);
            Event expected = eventDao.getEvents().get((long)4);
            Assert.assertTrue(eventsAreEqual(expected, actual));
        } catch (IncorrectEventException e) {
            Assert.fail("getById method threw IncorrectEventException with message:" + e.getMessage());
        }
    }

    @Test
    public void noSuchIdTest(){
        Event event = new Event();
        event.setId(6);
        try {
            Event actual = eventDao.getById(event);
            Assert.assertNull(actual);
        } catch (IncorrectEventException e) {
            Assert.fail("getById method threw IncorrectEventException with message:" + e.getMessage());
        }
    }

    @Test
    public void findEventsByIdTest(){
        Event event = new Event();
        event.setId(4);
        getFilmsAndCompare(event, 4);
    }

    @Test
    public void findEventsByNameTest(){
        Event event = new Event();
        event.setEventName("Jane Got a Gun");
        getFilmsAndCompare(event, 4);
    }

    @Test
    public void findEventsByCountryTest(){
        Event event = new Event();
        event.setCountry("USA");
        try {
            List<Event> actual = eventDao.findEvents(event);
            Assert.assertEquals(3, actual.size());
            long[] ids = {1, 2, 4};
            List<Event> expected = createListOfEvents(ids);
            listWithEventsAreEqual(actual, expected);
        }  catch (IncorrectEventException e) {
            Assert.fail("findEvents method threw IncorrectEventException with message:" + e.getMessage());
        }
    }

    private List<Event> createListOfEvents(long[] ids) {
        List<Event> expected = new ArrayList<>();
        for (long id: ids){
            Event event = eventDao.getEvents().get(id);
            expected.add(event);
        }
        return expected;
    }

    private void listWithEventsAreEqual(List<Event> expected, List<Event> actual) {
        for (int i = 0; i < actual.size(); i++){
            Event expectedEvent = expected.get(i);
            Event actualEvent = actual.get(i);
            Assert.assertTrue(eventsAreEqual(expectedEvent, actualEvent));
        }
    }

    @Test
    public void findEventsByYear(){
        Event event = new Event();
        event.setYear(2016);
        try {
            List<Event> actual = eventDao.findEvents(event);
            Assert.assertEquals(4, actual.size());
            long[] ids = {2, 3, 4, 5};
            List<Event> expected = createListOfEvents(ids);
            listWithEventsAreEqual(actual, expected);
        }  catch (IncorrectEventException e) {
            Assert.fail("findEvents method threw IncorrectEventException with message:" + e.getMessage());
        }
    }

    @Test
    public void addEventTest(){
        Event event = new Event("Flight", 2012, "USA", "Robert Zemeckis", 100);
        try {
            Event dbEvent = eventDao.getEvents().get(5);
            Assert.assertNull(dbEvent);
            eventDao.add(event);
            Assert.assertEquals(6, eventDao.getEvents().size());
            dbEvent = eventDao.getEvents().get((long)6);
            Assert.assertTrue(eventsAreEqual(dbEvent, event));
        } catch (IncorrectEventException e) {
            Assert.fail("addEvent method threw IncorrectEventException with message:" + e.getMessage());
        }
    }

    @Test
    public void findByRating(){
        Event event = new Event();
        event.setRating(Rating.HIGh);
        eventDao.getEvents().get((long)3).setRating((Rating.HIGh));
        eventDao.getEvents().get((long)2).setRating(Rating.LOW);
        eventDao.getEvents().get((long)1).setRating(Rating.HIGh);
        try {
            List<Event> actual = eventDao.findEvents(event);
            Assert.assertEquals(2, actual.size());
            long[] ids = {1, 3};
            List<Event> expected = createListOfEvents(ids);
            listWithEventsAreEqual(expected, actual);
        } catch (IncorrectEventException e) {
            Assert.fail("findEvents method threw IncorrectEventException with message:" + e.getMessage());
        }
    }

    private void getFilmsAndCompare(Event event, int dbEventId) {
        try {
            List<Event> actual = eventDao.findEvents(event);
            Assert.assertEquals(1, actual.size());
            Event expected = eventDao.getEvents().get((long)dbEventId);
            Assert.assertTrue(eventsAreEqual(expected, actual.get(0)));
        }  catch (IncorrectEventException e) {
            Assert.fail("findEvents method threw IncorrectEventException with message:" + e.getMessage());
        }
    }


    private boolean eventsAreEqual(Event event1, Event event2){
        if (event1.getId() != event2.getId()){
            return false;
        }
        if (!(strsAreEqual(event1.getCountry(), event2.getCountry()))){
            return false;
        }
        if (!(strsAreEqual(event1.getDirector(), event2.getDirector()))){
            return false;
        }
        if (!(strsAreEqual(event1.getEventName(), event2.getEventName()))){
            return false;
        }
        if (event1.getYear() != event2.getYear()){
            return false;
        }
        return true;
    }

    @Test
    public void removeEventTest(){
        Event event = new Event();
        event.setId(3);
        try {
            Event actual = eventDao.remove(event);
            Event expected = new Event(3, "Grimsby", 2016, "Great Britain", "Louis Leterrier", 150);
            Assert.assertTrue(eventsAreEqual(expected, actual));
            Assert.assertEquals(4, eventDao.getEvents().size());
            Event dbEvent = eventDao.getEvents().get((long)3);
            Assert.assertNull(dbEvent);
        } catch (IncorrectEventException e) {
            Assert.fail("removeEvent method threw IncorrectEventException with message:" + e.getMessage());
        }
    }

    @Test
    public void updateEventTest(){
        Event event = new Event(2, "Gattaca", 1997, "USA", "Andrew Niccol", 100);
        try {
            Event actual = eventDao.update(event);
            Assert.assertTrue(eventsAreEqual(event, actual));
            Assert.assertEquals(5, eventDao.getEvents().size());
        } catch (IncorrectEventException e) {
            Assert.fail("updateEvent method threw IncorrectEventException with message:" + e.getMessage());
        }
    }

    private boolean strsAreEqual(String str1, String str2){
        return (str1 == null ? str2 == null : str1.equals(str2));
    }
}
