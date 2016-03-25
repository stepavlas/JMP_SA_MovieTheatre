package com.stepavlas.movieTheatre;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Степан on 21.03.2016.
 */
public class Show extends Entity{
    private Event event;
    private Date dateTime;
    private Auditorium auditorium;
    private List<Ticket> purchasedSeats;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public Show() {
    }

    public Show(Event event, Date dateTime, Auditorium auditorium) {
        this.event = event;
        this.dateTime = dateTime;
        this.auditorium = auditorium;
        purchasedSeats = new ArrayList<>();
    }

    public Show(long id, Event event, Date dateTime, Auditorium auditorium) {
        this.id = id;
        this.event = event;
        this.dateTime = dateTime;
        this.auditorium = auditorium;
    }

    public List<Ticket> getPurchasedSeats() {
        return purchasedSeats;
    }

    public void setPurchasedSeats(List<Ticket> purchasedSeats) {
        this.purchasedSeats = purchasedSeats;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }
}
