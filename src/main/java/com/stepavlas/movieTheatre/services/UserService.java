package com.stepavlas.movieTheatre.services;

import com.stepavlas.movieTheatre.entities.Ticket;
import com.stepavlas.movieTheatre.entities.User;

import java.util.List;

/**
 * Created by admin on 17.03.2016.
 */
public interface UserService {
    void register(String firstName, String lastName, String email);
    User remove(String firstName, String lastName, String email);
    User getById(long id);
    User getUserByEmail(String email);
    List<User> getUsersByName(String firstName, String lastName);
    List<Ticket> getBookedTickets();
}
