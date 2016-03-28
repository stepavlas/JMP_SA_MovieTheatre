package com.stepavlas.movieTheatre.impl;

import com.stepavlas.movieTheatre.User;
import com.stepavlas.movieTheatre.UserDao;
import com.stepavlas.movieTheatre.exceptions.IncorrectUserException;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Viacheslav_Ganiaev on 3/28/2016.
 */
public class MapUserDaoImpl implements UserDao {
    private Map<Long, User> userMap = new HashMap<>();

    public void setUserMap(Map<Long, User> userMap) {
        this.userMap = userMap;
    }

    @Override
    public List<User> findUsers(User user) throws IncorrectUserException {
        throw new UnsupportedOperationException("MapUserDaoImpl:findUsers");
    }

    @Override
    public User getById(User user) throws IncorrectUserException {
        throw new UnsupportedOperationException("MapUserDaoImpl:getById");
    }

    @Override
    public void add(User user) throws IncorrectUserException {
        userMap.put(user.getId(), user);
    }

    @Override
    public User update(User user) throws IncorrectUserException {
        throw new UnsupportedOperationException("MapUserDaoImpl:update");
    }

    @Override
    public User remove(User user) throws IncorrectUserException {
        throw new UnsupportedOperationException("MapUserDaoImpl:remove");
    }
}
