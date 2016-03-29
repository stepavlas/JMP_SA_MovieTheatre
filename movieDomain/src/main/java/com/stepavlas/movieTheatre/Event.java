package com.stepavlas.movieTheatre;

import java.util.Date;

/**
 * Created by admin on 17.03.2016.
 */
public class Event extends Entity{
    private String eventName;
    private int year;
    private String country;
    private String director;
    private Rating rating;
    private int basePrice;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String filmName) {
        this.eventName = filmName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Event(long id, String eventName, int year, String country, String director, int basePrice) {
        this.id = id;
        this.eventName = eventName;
        this.country = country;
        this.director = director;
        this.year = year;
        this.basePrice = basePrice;
        rating = Rating.MID;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public Event() {
    }

    public Event(String eventName, int year, String country, String director, int basePrice) {
        this.eventName = eventName;
        this.year = year;
        this.country = country;
        this.director = director;
        this.basePrice = basePrice;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
