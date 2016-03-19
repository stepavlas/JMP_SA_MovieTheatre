package com.stepavlas.movieTheatre.dao;

import com.stepavlas.movieTheatre.entities.User;

import java.util.List;

/**
 * Created by Степан on 18.03.2016.
 */
public interface UserDao {
    List<User> findUsers(User user);

    User getById(User user);

    void add(User user);

    User update(User user);

    User remove(User user);
}
