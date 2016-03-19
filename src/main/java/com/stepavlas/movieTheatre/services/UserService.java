package com.stepavlas.movieTheatre.services;

import com.stepavlas.movieTheatre.dao.UserDao;
import com.stepavlas.movieTheatre.exceptions.IncorrectUserException;
import com.stepavlas.movieTheatre.entities.Ticket;
import com.stepavlas.movieTheatre.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 17.03.2016.
 */
public class UserService{

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void register(String firstName, String lastName, String email) {
        User user = new User(firstName, lastName, email);
        try {
            userDao.add(user);
        } catch (IncorrectUserException e){
            System.out.println("User with such data can't be registered: " + e.getMessage());
        }
    }

    public User remove(String firstName, String lastName, String email) {
        User user = new User(firstName, lastName, email);
        List<User> users = null;
        try {
            users = userDao.findUsers(user);
        } catch (IncorrectUserException e) {
            e.printStackTrace();
        }
        if (users.isEmpty() || users.size() == 0 || users.size() > 1){
            System.out.println("No users or more than one user were found. Can't remove user with such data");
            return null;
        }
        User foundUser = users.get(0);
        try {
            return userDao.remove(foundUser);
        } catch (IncorrectUserException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getById(long id) {
        User user = new User();
        user.setId(id);
        try {
            return userDao.getById(user);
        } catch (IncorrectUserException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        List<User> users = null;
        try {
            users = userDao.findUsers(user);
        } catch (IncorrectUserException e) {
            e.printStackTrace();
        }
        if (users == null || users.isEmpty()){
            return null;
        } else if (users.size() > 1){
            System.out.println("Unexpected case: more than one user was found.");
            return null;
        }
        return users.get(0);
    }

    public List<User> getUsersByName(String firstName, String lastName) {
        if (firstName == null || lastName == null){
            System.out.println("Mandatory value for search by name wasn't entered");
            return null;
        }
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        List<User> users = new ArrayList<>();
        try {
            users = userDao.findUsers(user);
        } catch (IncorrectUserException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<Ticket> getBookedTickets() {
        return null;
    }
}
