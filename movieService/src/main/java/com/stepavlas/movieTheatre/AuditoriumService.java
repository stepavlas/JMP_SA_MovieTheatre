package com.stepavlas.movieTheatre;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Степан on 21.03.2016.
 */
public class AuditoriumService {
    List<Auditorium> auditoriums;

    public void setAuditoriums(List<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    public int getSeatsNumber(String auditoriumName){
        for (Auditorium auditorium: auditoriums){
            if (auditorium.getName().equals(auditoriumName)){
                return auditorium.getNumberOfSeats();
            }
        }
        System.out.println("no such auditory");
        return -1;
    }

    public String getVipSeats(String auditoriumName){
        for (Auditorium auditorium: auditoriums){
            if (auditorium.getName().equals(auditoriumName)){
                return auditorium.getVipSeats();
            }
        }
        System.out.println("no such auditory");
        return null;
    }

    public List<Auditorium> getAuditoriums() {
        return auditoriums;
    }
}
