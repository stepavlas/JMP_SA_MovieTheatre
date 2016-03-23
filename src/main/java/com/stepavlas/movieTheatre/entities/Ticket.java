package com.stepavlas.movieTheatre.entities;

import java.util.Date;

/**
 * Created by admin on 17.03.2016.
 */
public class Ticket {
    private Show show;
    private int row;
    private int place;

    public Ticket(Show show, int row, int place) {
        this.show = show;
        this.row = row;
        this.place = place;
    }

    public int getPlace(){
        return row * place;
    }
}
