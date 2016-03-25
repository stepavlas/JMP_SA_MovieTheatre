package com.stepavlas.movieTheatre;

import java.util.Date;

/**
 * Created by admin on 17.03.2016.
 */
public class User extends Entity{
    private String firstName;
    private String lastName;
    private String email;
    private Date birthDate;
    private int numTickets;

    public User() {
    }

    public User(String firstName, String lastName, String email, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        numTickets = 0;
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(long id, String firstName, String lastName, String email, Date birthDate) {
        this(firstName, lastName, email, birthDate);
        this.id = id;
    }

    public int getNumTickets() {
        return numTickets;
    }

    public void setNumTickets(int numTickets) {
        this.numTickets = numTickets;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }



    @Override
    public String toString() {
        return "user: " + email;
    }
}
