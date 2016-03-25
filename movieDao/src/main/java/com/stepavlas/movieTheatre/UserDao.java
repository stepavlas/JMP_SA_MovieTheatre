package com.stepavlas.movieTheatre;

import com.stepavlas.movieTheatre.exceptions.IncorrectUserException;

import java.util.List;

/**
 * Created by Степан on 18.03.2016.
 */
public interface UserDao {
    List<User> findUsers(User user) throws IncorrectUserException;

    User getById(User user) throws IncorrectUserException;

    void add(User user) throws IncorrectUserException;

    User update(User user) throws IncorrectUserException;

    User remove(User user) throws IncorrectUserException;
}
