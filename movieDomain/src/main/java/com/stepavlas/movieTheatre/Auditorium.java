package com.stepavlas.movieTheatre;

/**
 * Created by admin on 17.03.2016.
 */
public class Auditorium {
    private String name;
    private int numberOfSeats;
    private String vipSeats;

    public Auditorium(String name, int numberOfSeats, String vipSeats) {
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.vipSeats = vipSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auditorium)) return false;

        Auditorium that = (Auditorium) o;

        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(String vipSeats) {
        this.vipSeats = vipSeats;
    }

    @Override
    public String toString() {
        return "Auditorium{" +
                "name='" + name + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", vipSeats='" + vipSeats + '\'' +
                '}';
    }
}